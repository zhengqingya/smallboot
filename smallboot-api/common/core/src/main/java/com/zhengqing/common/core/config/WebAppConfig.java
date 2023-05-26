package com.zhengqing.common.core.config;

import com.zhengqing.common.auth.config.SaTokenProperty;
import com.zhengqing.common.core.config.interceptor.HandlerInterceptorForTenantId;
import com.zhengqing.common.core.config.interceptor.HandlerInterceptorForToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * <p>
 * 注册拦截器
 * </p>
 *
 * @author zhengqingya
 * @description eg: 租户ID
 * @date 2021/1/13 14:41
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Resource
    private SaTokenProperty saTokenProperty;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(new HandlerInterceptorForToken(this.saTokenProperty)).addPathPatterns("/**");
        registry.addInterceptor(new HandlerInterceptorForTenantId()).addPathPatterns("/**");
    }

}
