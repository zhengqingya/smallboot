package com.zhengqing.common.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * <p> 全局配置解决跨域 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/22 9:09
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        // 允许任何来源
        corsConfig.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        // 允许任何请求头
        corsConfig.addAllowedHeader(CorsConfiguration.ALL);
        // 允许任何方法
        corsConfig.addAllowedMethod(CorsConfiguration.ALL);
        // 允许凭证
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 设置允许跨域请求的路由
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsWebFilter(source);
    }

}
