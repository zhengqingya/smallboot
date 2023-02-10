package com.zhengqing.common.core.custom.requestparamalias;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 请求的字段别名-参数解析器 </p>
 *
 * @author zhengqingya
 * @description 添加到第一个参数解析器中的bean配置，通过自定义spring属性编辑器解决
 * @date 2022/10/20 10:58
 */
@Configuration
public class RequestParamAliasConfig {
    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public RequestParamAliasProcessor requestParamAliasProcessor() {
        return new RequestParamAliasProcessor(this.applicationContext);
    }

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
                if (bean instanceof RequestMappingHandlerAdapter) {
                    RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
                    List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
                    resolvers.add(RequestParamAliasConfig.this.requestParamAliasProcessor());
                    if (adapter.getArgumentResolvers() != null) {
                        resolvers.addAll(adapter.getArgumentResolvers());
                    }
                    adapter.setArgumentResolvers(resolvers);
                }
                return bean;
            }
        };
    }
}
