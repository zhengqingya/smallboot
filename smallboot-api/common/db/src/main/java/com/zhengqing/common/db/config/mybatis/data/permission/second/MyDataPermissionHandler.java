package com.zhengqing.common.db.config.mybatis.data.permission.second;

import cn.hutool.core.util.StrUtil;
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
import java.util.Set;
import java.util.stream.Collectors;


/**
 * <p> mybatis-plus 数据权限处理器 </p>
 *
 * @author zhengqingya
 * @description {@link MyDataPermissionInterceptor}
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
                // 查看全部
                case ALL:
                    return where;
                // 查看本人所在角色以及下属机构
                case ROLE_AUTO:
                    // 创建IN 表达式
                    // 创建IN范围的元素集合
                    Set<String> roleIdList = userPermissionInfo.getRoleIdList();
                    // 把集合转变为JSQLParser需要的元素列表
                    ItemsList itemsList = new ExpressionList(roleIdList.stream().map(LongValue::new).collect(Collectors.toList()));
                    InExpression inExpression = new InExpression(new Column(mainTableName + ".create_role_id"), itemsList);
                    AndExpression andExpression = new AndExpression(where, inExpression);
                    log.info(" where {}", andExpression);
                    return andExpression;
                // 查看当前角色的数据
                case ROLE:
                    //  = 表达式
                    // role_id = roleId
                    EqualsTo equalsTo = new EqualsTo();
                    equalsTo.setLeftExpression(new Column(mainTableName + ".create_role_id"));
                    equalsTo.setRightExpression(new LongValue(userPermissionInfo.getRoleId()));
                    // 创建 AND 表达式 拼接Where 和 = 表达式
                    // WHERE xxx AND role_id = 3
                    AndExpression deptAndExpression = new AndExpression(where, equalsTo);
                    log.info(" where {}", deptAndExpression);
                    return deptAndExpression;
                // 查看自己的数据
                case SELF:
                    // create_by = userId
                    EqualsTo selfEqualsTo = new EqualsTo();
                    selfEqualsTo.setLeftExpression(new Column(mainTableName + ".create_by"));
                    selfEqualsTo.setRightExpression(new LongValue(userPermissionInfo.getUserId()));
                    AndExpression selfAndExpression = new AndExpression(where, selfEqualsTo);
                    log.info(" where {}", selfAndExpression);
                    return selfAndExpression;
                case AUTO:
                    return new AndExpression(where, new StringValue(userPermissionInfo.getSql()));
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
