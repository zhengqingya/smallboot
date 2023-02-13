package com.zhengqing.common.auth.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> Sa-Token 配置类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/11/3 12:06
 */
@Configuration
public class SaTokenConfig {

    /**
     * Sa-Token 整合 jwt (Simple 简单模式)
     * 参考 https://sa-token.cc/doc.html#/plugin/jwt-extend
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

}
