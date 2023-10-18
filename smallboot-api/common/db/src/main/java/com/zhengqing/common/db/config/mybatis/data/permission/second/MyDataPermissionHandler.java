package com.zhengqing.common.db.config.mybatis.data.permission.second;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.db.context.DataPermissionThreadLocal;
import com.zhengqing.common.db.enums.DataPermissionTypeEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p> mybatis-plus 数据权限处理器 </p>
 *
 * @author zhengqingya
 * @description {@link MyDataPermissionInterceptor}
 * 使用示例： 在需要的地方设置即可 DataPermissionThreadLocal.set(UserPermissionInfo.builder().dataPermissionTypeEnum(DataPermissionTypeEnum.SELF).build());
 * @date 2022/1/10 17:37
 */
@Slf4j
public class MyDataPermissionHandler {

    /**
     * 获取数据权限 SQL 片段
     *
     * @param plainSelect  查询对象
     * @param whereSegment 查询条件片段
     * @return JSqlParser 条件表达式
     */
    @SneakyThrows(Exception.class)
    public Expression getSqlSegment(PlainSelect plainSelect, String whereSegment) {
        // 待执行 SQL Where 条件表达式
        Expression where = plainSelect.getWhere();
        // 获取权限过滤相关信息
        UserPermissionInfo userPermissionInfo = DataPermissionThreadLocal.get();
        // 防止下次进来时非上次数据认证权限！！！
        DataPermissionThreadLocal.remove();
        if (userPermissionInfo == null) {
            return where;
        }
        Table fromItem = (Table) plainSelect.getFromItem();
        // 有别名用别名，无别名用表名，防止字段冲突报错
        Alias fromItemAlias = fromItem.getAlias();
        String mainTableName = fromItemAlias == null ? fromItem.getName() : fromItemAlias.getName();

        // 获取mapper层信息
        List<String> split = StrUtil.split(whereSegment, '.');
        int index = split.size();
        String method = split.get(index - 1);
        String mapper = split.get(index - 2);


        try {
            DataPermissionTypeEnum dataPermissionTypeEnum = userPermissionInfo.getDataPermissionTypeEnum();
            log.info("[数据权限过滤] dataPermissionType:[{}]  where:[{}]  whereSegment:[{}]", dataPermissionTypeEnum, where, whereSegment);
            Expression expression = new HexValue(" 1 = 1 ");
            if (where == null) {
                where = expression;
            }

            // 根据不同类型进行权限处理
            switch (dataPermissionTypeEnum) {
                case 全部可见:
                    return where;
                case 本人可见:
                    // create_by = userId
                    EqualsTo selfEqualsTo = new EqualsTo();
                    selfEqualsTo.setLeftExpression(new Column(mainTableName + ".create_by"));
                    selfEqualsTo.setRightExpression(new LongValue(JwtUserContext.getUserId()));
                    AndExpression selfAndExpression = new AndExpression(where, selfEqualsTo);
                    log.info(" where {}", selfAndExpression);
                    return selfAndExpression;
                case 自定义sql过滤:
                    return new AndExpression(where, new StringValue(userPermissionInfo.getSql()));
                case 本人所属角色:
                    //  = 表达式
                    // role_id = roleId
                    EqualsTo equalsTo = new EqualsTo();
                    equalsTo.setLeftExpression(new Column(mainTableName + ".role_id"));
                    equalsTo.setRightExpression(new LongValue(userPermissionInfo.getRoleId()));
                    // 创建 AND 表达式 拼接Where 和 = 表达式
                    // WHERE xxx AND role_id = 3
                    AndExpression deptAndExpression = new AndExpression(where, equalsTo);
                    log.info(" where {}", deptAndExpression);
                    return deptAndExpression;
                case 自定义角色:
                    // 创建IN 表达式
                    // 创建IN范围的元素集合
                    List<Integer> roleIdList = JwtUserContext.get().getAllRoleIdList();
                    if (roleIdList.contains(AppConstant.SMALL_BOOT_SUPER_ADMIN_ROLE_ID)) {
                        return where;
                    }
                    if (CollUtil.isEmpty(roleIdList)) {
                        roleIdList = Lists.newArrayList(-1);
                    }
                    // 把集合转变为JSQLParser需要的元素列表
                    ItemsList itemsList = new ExpressionList(roleIdList.stream().map(LongValue::new).collect(Collectors.toList()));
                    InExpression inExpression = new InExpression(new Column(mainTableName + ".role_id"), itemsList);
                    AndExpression andExpression = new AndExpression(where, inExpression);
                    log.info(" where {}", andExpression);
                    return andExpression;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("MyDataPermissionHandler 数据权限处理异常：", e);
        } finally {

        }
        return where;
    }


}
