package com.zhengqing.common.core.custom.post;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <p>
 * 注册参数解析器
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/13 14:41
 */
@Configuration
public class RequestPostSingleParamResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestPostSingleParamMethodArgumentResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }

}
