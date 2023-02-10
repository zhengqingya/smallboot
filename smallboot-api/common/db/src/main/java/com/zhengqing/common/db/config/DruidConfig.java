//package com.zhengqing.common.db.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.aop.support.JdkRegexpMethodPointcut;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * <p>
// * Druid核心配置类 - 注册bean
// * </p>
// *
// * @author zhengqingya
// * @description Druid连接池监控平台 http://127.0.0.1:5000/druid/index.html
// * @date 2019/12/19 18:20
// */
//@Configuration
//public class DruidConfig {
//
//    @Value("${spring.datasource.druid.stat-view-servlet.login-username}")
//    private String loginUsername;
//
//    @Value("${spring.datasource.druid.stat-view-servlet.login-password}")
//    private String loginPassword;
//
//    /**
//     * 配置Druid监控
//     */
//    @Bean
//    public ServletRegistrationBean druidServlet() {
//        // 注册服务
//        ServletRegistrationBean servletRegistrationBean =
//                new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//        // IP白名单(为空表示,所有的都可以访问,多个IP的时候用逗号隔开)
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
//        // IP黑名单 (存在共同时，deny优先于allow)
//        servletRegistrationBean.addInitParameter("deny", "127.0.0.2");
//        // 设置控制台登录的用户名和密码
//        servletRegistrationBean.addInitParameter("loginUsername", this.loginUsername);
//        servletRegistrationBean.addInitParameter("loginPassword", this.loginPassword);
//        // 是否能够重置数据
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }
//
//    /**
//     * 配置web监控的filter
//     */
//    @Bean
//    public FilterRegistrationBean webStatFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        // 添加过滤规则
//        Map<String, String> initParams = new HashMap<>(1);
//        // 设置忽略请求
//        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
//        filterRegistrationBean.setInitParameters(initParams);
//        filterRegistrationBean.addInitParameter("profileEnable", "true");
//        filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
//        filterRegistrationBean.addInitParameter("principalSessionName", "");
//        filterRegistrationBean.addInitParameter("aopPatterns", "com.example.demo.service");
//        // 验证所有请求
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }
//
//    /**
//     * 配置数据源 【 将所有前缀为spring.datasource下的配置项都加载到DataSource中 】
//     */
//    @Bean(name = "dataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
//    public DataSource dataSource() {
//        return new DruidDataSource();
//    }
//
//    // @Bean(name = "dataSource2")
//    // @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.db-test")
//    // public DataSource dataSource2() {
//    // return new DruidDataSource();
//    // }
//
//    /**
//     * 配置事物管理器
//     */
//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(this.dataSource());
//    }
//
//    // @Bean(name = "transactionManager2")
//    // public DataSourceTransactionManager transactionManager2() {
//    // return new DataSourceTransactionManager(dataSource2());
//    // }
//
//    /**
//     * ↓↓↓↓↓↓ 配置spring监控 ↓↓↓↓↓↓
//     * DruidStatInterceptor: druid提供的拦截器
//     */
//    @Bean
//    public DruidStatInterceptor druidStatInterceptor() {
//        DruidStatInterceptor dsInterceptor = new DruidStatInterceptor();
//        return dsInterceptor;
//    }
//
//    /**
//     * 使用正则表达式配置切点
//     */
//    @Bean
//    @Scope("prototype")
//    public JdkRegexpMethodPointcut druidStatPointcut() {
//        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
//        pointcut.setPattern("com.zhengqing.*.api.*");
//        return pointcut;
//    }
//
//    /**
//     * DefaultPointcutAdvisor类定义advice及 pointcut 属性。advice指定使用的通知方式，也就是druid提供的DruidStatInterceptor类，pointcut指定切入点
//     */
//    @Bean
//    public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor,
//                                                   JdkRegexpMethodPointcut druidStatPointcut) {
//        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
//        defaultPointAdvisor.setPointcut(druidStatPointcut);
//        defaultPointAdvisor.setAdvice(druidStatInterceptor);
//        return defaultPointAdvisor;
//    }
//
//}
