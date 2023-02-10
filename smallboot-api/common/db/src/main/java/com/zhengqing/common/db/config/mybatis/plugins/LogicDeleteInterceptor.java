package com.zhengqing.common.db.config.mybatis.plugins;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.zhengqing.common.db.config.mybatis.MybatisPlusConfig;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.io.StringReader;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * <p>
 * mybatis-plus 逻辑删除插件
 * </p>
 *
 * @author zhengqingya
 * @description 官方：https://baomidou.com/pages/6b03c5/#%E4%BD%BF%E7%94%A8%E6%96%B9%E6%B3%95
 * 主要解决在xml中写的sql 逻辑删除 失效问题
 * @date 2020/5/20 17:14
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class LogicDeleteInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 判断是不是SELECT操作 不是直接过滤
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 执行的SQL语句
        String originalSql = boundSql.getSql();
        // SQL语句的参数
        Object parameterObject = boundSql.getParameterObject();

        String finalSql = this.handleSql(originalSql);
//        System.err.println("逻辑删除处理过后的SQL: \n" + finalSql);

        metaObject.setValue("delegate.boundSql.sql", finalSql);
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
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof SubSelect) {
            // mybatis-plus分页时会查询count(*)查询总数 会有嵌套 因此这里需要处理下...
            this.setWhere((PlainSelect) ((SubSelect) fromItem).getSelectBody());
            return;
        }
        Table fromItemOfTable = (Table) fromItem;
        // 有别名用别名，无别名用表名，防止字段冲突报错
        Alias fromItemAlias = fromItemOfTable.getAlias();
        String originalTableName = fromItemOfTable.getName();
        String mainTableName = fromItemAlias == null ? originalTableName : fromItemAlias.getName();

        // 判断是否需要逻辑删除,如果不需要直接过滤
        if (!MybatisPlusConfig.LOGIC_DELETE_TABLE.contains(originalTableName)) {
            return;
        }

        // 构建子查询 -- 逻辑删除
        String dataSql = mainTableName + ".is_deleted = 0";
        if (plainSelect.getWhere() == null) {
            plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression(dataSql));
        } else {
            plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), CCJSqlParserUtil.parseCondExpression(dataSql)));
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


}
