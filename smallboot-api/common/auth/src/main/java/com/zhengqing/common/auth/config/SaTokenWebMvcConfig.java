package com.zhengqing.common.auth.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * <p> 注册 Sa-Token 路由拦截器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/11/3 12:06
 */
@Configuration
public class SaTokenWebMvcConfig implements WebMvcConfigurer {

    @Resource
    private SaTokenProperty saTokenProperty;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册1个登录认证拦截器 -- 参考： https://sa-token.cc/doc.html#/use/route-check
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns(this.saTokenProperty.getInterceptUrlList())
                .excludePathPatterns(this.saTokenProperty.getOpenUrlList());
    }

}
