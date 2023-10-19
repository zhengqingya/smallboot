package com.zhengqing.common.db.config.mybatis.data.permission.second;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import com.zhengqing.common.base.model.bo.ScopeDataBO;
import com.zhengqing.common.db.enums.DataPermissionTypeEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p> mybatis-plus 数据权限处理器 </p>
 *
 * @author zhengqingya
 * @description {@link MyDataPermissionInterceptor}
 * 通过全局角色关联的数据权限来判断
 * @date 2022/1/10 17:37
 */
@Slf4j
public class MyDataPermissionHandler {

    /**
     * 获取数据权限 SQL 片段
     *
     * @param plainSelect     查询对象
     * @param mapperClassName mapper全限定类名
     * @return JSqlParser 条件表达式
     */
    @SneakyThrows(Exception.class)
    public Expression getSqlSegment(PlainSelect plainSelect, String mapperClassName) {
        // 待执行 SQL Where 条件表达式
        Expression where = plainSelect.getWhere();

        // 拿到用户绑定的数据权限
        JwtUserBO jwtUserBO = JwtUserContext.get();
        if (jwtUserBO == null) {
            return where;
        }
        List<ScopeDataBO> scopeDataList = jwtUserBO.getScopeDataList();
        if (CollUtil.isEmpty(scopeDataList)) {
            return where;
        }
        // 过滤下 -- 只要全限定名全等的数据
        scopeDataList = scopeDataList.stream().filter(e -> {
            String scopeClass = e.getScopeClass();
            List<String> scopeClassList = Arrays.asList(scopeClass.replaceAll("\n", "").replaceAll("#", ".").split(","));
            scopeClassList.forEach(String::trim);
            return scopeClassList.contains(mapperClassName);
        }).collect(Collectors.toList());
        if (CollUtil.isEmpty(scopeDataList)) {
            return where;
        }
        String userId = jwtUserBO.getUserId();
        List<Integer> userReRoleIdList = jwtUserBO.getRoleIdList();
        List<Integer> userReAllRoleIdList = jwtUserBO.getAllRoleIdList();
        Integer deptId = jwtUserBO.getDeptId();

        Table fromItem = (Table) plainSelect.getFromItem();
        // 有别名用别名，无别名用表名，防止字段冲突报错
        Alias fromItemAlias = fromItem.getAlias();
        String mainTableName = fromItemAlias == null ? fromItem.getName() : fromItemAlias.getName();

        // 获取mapper层信息
        List<String> split = StrUtil.split(mapperClassName, '.');
        int index = split.size();
        String method = split.get(index - 1);
        String mapper = split.get(index - 2);

        // 开始数据过滤逻辑
        try {
            for (ScopeDataBO item : scopeDataList) {
                String scopeColumn = item.getScopeColumn();
                String scopeVisibleField = item.getScopeVisibleField();
                String scopeValue = item.getScopeValue();
                DataPermissionTypeEnum dataPermissionTypeEnum = DataPermissionTypeEnum.getEnum(item.getScopeType());
                log.info("[数据权限过滤] dataPermissionType:[{}]  where:[{}]  mapperClassName:[{}]", dataPermissionTypeEnum, where, mapperClassName);
                Expression expression = new HexValue(" 1 = 1 ");
                if (where == null) {
                    where = expression;
                }

                // 根据不同类型进行权限处理
                switch (dataPermissionTypeEnum) {
                    case 全部可见:
                        return where;
                    case 本人可见:
                        // create_by = ?
                        EqualsTo userSelfEqualsTo = new EqualsTo();
                        userSelfEqualsTo.setLeftExpression(new Column(mainTableName + ".create_by"));
                        userSelfEqualsTo.setRightExpression(new LongValue(userId));
                        AndExpression userSelfAndExpression = new AndExpression(where, userSelfEqualsTo);
                        log.info(" where {}", userSelfAndExpression);
                        return userSelfAndExpression;
                    case 所在部门可见:
                        EqualsTo deptSelfEqualsTo = new EqualsTo();
                        deptSelfEqualsTo.setLeftExpression(new Column(mainTableName +
                                ("t_sys_dept".equals(mainTableName) ? ".id" : ".dept_id")
                        ));
                        deptSelfEqualsTo.setRightExpression(new LongValue(deptId));
                        AndExpression deptSelfAndExpression = new AndExpression(where, deptSelfEqualsTo);
                        log.info(" where {}", deptSelfAndExpression);
                        return deptSelfAndExpression;
//                    case 自定义sql过滤:
//                        return new AndExpression(where, new StringValue(scopeValue));
//                    case 本人角色:
//                        //  = 表达式
//                        // role_id = roleId
//                        EqualsTo equalsTo = new EqualsTo();
//                        equalsTo.setLeftExpression(new Column(mainTableName + ".role_id"));
//                        equalsTo.setRightExpression(new LongValue(userPermissionInfo.getRoleId()));
//                        // 创建 AND 表达式 拼接Where 和 = 表达式
//                        // WHERE xxx AND role_id = 3
//                        AndExpression deptAndExpression = new AndExpression(where, equalsTo);
//                        log.info(" where {}", deptAndExpression);
//                        return deptAndExpression;
                    case 所在角色以及下级角色:
                        // 创建IN 表达式
                        // 创建IN范围的元素集合
                        if (userReAllRoleIdList.contains(AppConstant.SMALL_BOOT_SUPER_ADMIN_ROLE_ID)) {
                            return where;
                        }
                        if (CollUtil.isEmpty(userReAllRoleIdList)) {
                            userReAllRoleIdList = Lists.newArrayList(-1);
                        }
                        // 把集合转变为JSQLParser需要的元素列表
                        ItemsList itemsList = new ExpressionList(userReAllRoleIdList.stream().map(LongValue::new).collect(Collectors.toList()));
                        InExpression inExpression = new InExpression(new Column(mainTableName + ".role_id"), itemsList);
                        AndExpression andExpression = new AndExpression(where, inExpression);
                        log.info(" where {}", andExpression);
                        return andExpression;
                    default:
                        break;
                }

            }
        } catch (Exception e) {
            log.error("MyDataPermissionHandler 数据权限处理异常：", e);
        } finally {

        }

        return where;
    }


}
