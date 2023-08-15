package com.zhengqing.common.auth.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

/**
 * <p> 注册 Sa-Token 路由拦截器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/11/3 12:06
 */
@Configuration
@RequiredArgsConstructor
public class SaTokenWebMvcConfig implements WebMvcConfigurer {

    private final SaTokenProperty saTokenProperty;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        this.getApiOpenList();

        // 注册1个登录认证拦截器 -- 参考： https://sa-token.cc/doc.html#/use/route-check
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns(this.saTokenProperty.getInterceptUrlList())
                .excludePathPatterns(this.saTokenProperty.getOpenUrlList());
    }


    private List<String> getApiOpenList() {
        List<String> apiOpenList = Lists.newArrayList();
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Map<String, Object> apiOpenAnnotationMap = applicationContext.getBeansWithAnnotation(RestController.class);
        apiOpenAnnotationMap.forEach((key, value) -> {

        });
        return apiOpenList;
    }


}
