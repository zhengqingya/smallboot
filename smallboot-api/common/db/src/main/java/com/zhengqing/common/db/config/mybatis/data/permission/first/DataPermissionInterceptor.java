package com.zhengqing.common.db.config.mybatis.data.permission.first;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import javax.sql.DataSource;
import java.io.StringReader;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;


/**
 * <p> mybatis-plus 数据权限插件 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/12 14:36
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataPermissionInterceptor implements Interceptor {

    /**
     * 数据源
     */
    private DataSource dataSource;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 先判断是不是SELECT操作 不是直接过滤
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 执行的SQL语句
        String originalSql = boundSql.getSql();
        // SQL语句的参数
        Object parameterObject = boundSql.getParameterObject();

        // TODO 这里对执行SQL进行自定义处理...
//        String finalSql = this.handleSql(originalSql);
//        System.err.println("数据权限处理过后的SQL: " + finalSql);

        metaObject.setValue("delegate.boundSql.sql", originalSql);
        return invocation.proceed();
    }


    /**
     * 改写SQL
     * {@link com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor}
     *
     * @param originalSql 执行的SQL语句
     * @return 处理后的SQL
     * @author zhengqingya
     * @date 2022/1/13 10:43
     */
    private String handleSql(String originalSql) throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(originalSql));
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect) {
            this.setWhere((PlainSelect) selectBody);
        } else if (selectBody instanceof SetOperationList) {
            SetOperationList setOperationList = (SetOperationList) selectBody;
            List<SelectBody> selectBodyList = setOperationList.getSelects();
            selectBodyList.forEach(s -> this.setWhere((PlainSelect) s));
        }
        return select.toString();
    }

    /**
     * 设置 where 条件  --  使用CCJSqlParser将原SQL进行解析并改写
     *
     * @param plainSelect 查询对象
     */
    @SneakyThrows(Exception.class)
    protected void setWhere(PlainSelect plainSelect) {
        Table fromItem = (Table) plainSelect.getFromItem();
        // 有别名用别名，无别名用表名，防止字段冲突报错
        Alias fromItemAlias = fromItem.getAlias();
        String mainTableName = fromItemAlias == null ? fromItem.getName() : fromItemAlias.getName();
        // 构建子查询 -- 数据权限过滤SQL
        String dataPermissionSql = mainTableName + ".create_by in ( 1, 2, 3 )";
        if (plainSelect.getWhere() == null) {
            plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression(dataPermissionSql));
        } else {
            plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), CCJSqlParserUtil.parseCondExpression(dataPermissionSql)));
        }
    }


    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 查找参数是否包括DataScope对象
     *
     * @param parameterObj 参数列表
     * @return DataScope
     */
    //    private DataScope findDataScopeObject(Object parameterObj) {
    //        if (parameterObj instanceof DataScope) {
    //            return (DataScope) parameterObj;
    //        } else if (parameterObj instanceof Map) {
    //            for (Object val : ((Map<?, ?>) parameterObj).values()) {
    //                if (val instanceof DataScope) {
    //                    return (DataScope) val;
    //                }
    //            }
    //        }
    //        return null;
    //    }

}
