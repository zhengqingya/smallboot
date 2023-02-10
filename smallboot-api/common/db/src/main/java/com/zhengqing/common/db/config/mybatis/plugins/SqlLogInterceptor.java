package com.zhengqing.common.db.config.mybatis.plugins;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * <p>
 * 用于输出每条 SQL 语句及其执行时间
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/5/20 17:14
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = Statement.class),
        @Signature(type = StatementHandler.class, method = "batch", args = Statement.class)})
public class SqlLogInterceptor implements Interceptor {

    private static final String DRUID_POOLED_PREPARED_STATEMENT = "com.alibaba.druid.pool.DruidPooledPreparedStatement";
    private static final String T4C_PREPARED_STATEMENT = "oracle.jdbc.driver.T4CPreparedStatement";
    private static final String ORACLE_PREPARED_STATEMENT_WRAPPER = "oracle.jdbc.driver.OraclePreparedStatementWrapper";

    private Method oracleGetOriginalSqlMethod;
    private Method druidGetSqlMethod;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            statement = (Statement) stmtMetaObj.getValue("stmt.statement");
        } catch (Exception e) {
            // do nothing
        }
        if (stmtMetaObj.hasGetter("delegate")) {
            // Hikari
            try {
                statement = (Statement) stmtMetaObj.getValue("delegate");
            } catch (Exception ignored) {

            }
        }

        // tips: `originalSql`为包含？预编译的sql
        String originalSql = null;
        String stmtClassName = statement.getClass().getName();
        if (DRUID_POOLED_PREPARED_STATEMENT.equals(stmtClassName)) {
            try {
                if (this.druidGetSqlMethod == null) {
                    Class<?> clazz = Class.forName(DRUID_POOLED_PREPARED_STATEMENT);
                    this.druidGetSqlMethod = clazz.getMethod("getSql");
                }
                Object stmtSql = this.druidGetSqlMethod.invoke(statement);
                if (stmtSql instanceof String) {
                    originalSql = (String) stmtSql;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (T4C_PREPARED_STATEMENT.equals(stmtClassName)
                || ORACLE_PREPARED_STATEMENT_WRAPPER.equals(stmtClassName)) {
            try {
                if (this.oracleGetOriginalSqlMethod != null) {
                    Object stmtSql = this.oracleGetOriginalSqlMethod.invoke(statement);
                    if (stmtSql instanceof String) {
                        originalSql = (String) stmtSql;
                    }
                } else {
                    Class<?> clazz = Class.forName(stmtClassName);
                    this.oracleGetOriginalSqlMethod = this.getMethodRegular(clazz, "getOriginalSql");
                    if (this.oracleGetOriginalSqlMethod != null) {
                        // OraclePreparedStatementWrapper is not a public class, need set this.
                        this.oracleGetOriginalSqlMethod.setAccessible(true);
                        if (null != this.oracleGetOriginalSqlMethod) {
                            Object stmtSql = this.oracleGetOriginalSqlMethod.invoke(statement);
                            if (stmtSql instanceof String) {
                                originalSql = (String) stmtSql;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // ignore
            }
        }
        if (originalSql == null) {
            originalSql = statement.toString();
        }
        originalSql = originalSql.replaceAll("[\\s]+", StringPool.SPACE);
        int index = this.indexOfSqlStart(originalSql);
        if (index > 0) {
            // 这里拿到执行sql
            originalSql = originalSql.substring(index);
        }

        // 计算执行 SQL 耗时
        long start = SystemClock.now();
        Object result = invocation.proceed();
        long timing = SystemClock.now() - start;

        // SQL 打印执行结果
        Object target = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 最终sql
        String finalSql = showSql((MybatisConfiguration) metaObject.getValue("delegate.configuration"), (BoundSql) metaObject.getValue("delegate.boundSql"));

        // 打印 sql
        System.err.println(
                String.format(
                        "\n==============  Sql Start  ==============" +
                                "\nExecute ID  ：%s" +
                                "\nExecute SQL ：%s" +
                                "\nExecute Time：%s ms" +
                                "\n==============  Sql  End   ==============\n",
                        ms.getId(), finalSql, timing));
        return result;
    }

    /**
     * 进行？的替换
     */
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                        Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 如果参数是String，则添加单引号，
     * 如果是日期，则转换为时间格式器并加单引号；
     * 对参数是null和不是null的情况作了处理
     */
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 获取此方法名的具体 Method
     *
     * @param clazz      class 对象
     * @param methodName 方法名
     * @return 方法
     */
    private Method getMethodRegular(Class<?> clazz, String methodName) {
        if (Object.class.equals(clazz)) {
            return null;
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return this.getMethodRegular(clazz.getSuperclass(), methodName);
    }

    /**
     * 获取sql语句开头部分
     *
     * @param sql ignore
     * @return ignore
     */
    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Comparator.naturalOrder());
        return list.get(0);
    }

    /**
     * 匹配sql -- 拿到表名
     *
     * @param sql sql
     * @return 匹配后的sql表名
     * @author zhengqingya
     * @date 2020/12/2 17:14
     */
//    private String matchSql(String sql) {
//        Matcher matcher = null;
//        // SELECT 列名称 FROM 表名称
//        // SELECT * FROM 表名称
//        if (sql.startsWith("SELECT")) {
//            matcher = Pattern.compile("SELECT\\s.+FROM\\s(.+)WHERE\\s(.*)").matcher(sql);
//            if (matcher.find()) {
//                return matcher.group(1);
//            }
//        }
//        // INSERT INTO 表名称 VALUES (值1, 值2,....)
//        // INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
//        if (sql.startsWith("INSERT")) {
//            matcher = Pattern.compile("INSERT\\sINTO\\s(.+)\\(.*\\)\\s.*").matcher(sql);
//            if (matcher.find()) {
//                return matcher.group(1);
//            }
//        }
//        // UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
//        if (sql.startsWith("UPDATE")) {
//            matcher = Pattern.compile("UPDATE\\s(.+)SET\\s.*").matcher(sql);
//            if (matcher.find()) {
//                return matcher.group(1);
//            }
//        }
//        // DELETE FROM 表名称 WHERE 列名称 = 值
//        if (sql.startsWith("DELETE")) {
//            matcher = Pattern.compile("DELETE\\sFROM\\s(.+)WHERE\\s(.*)").matcher(sql);
//            if (matcher.find()) {
//                return matcher.group(1);
//            }
//        }
//        return null;
//    }

}
