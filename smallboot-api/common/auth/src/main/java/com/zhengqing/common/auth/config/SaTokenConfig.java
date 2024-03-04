package com.zhengqing.common.auth.config;

import cn.dev33.satoken.strategy.SaStrategy;
import cn.hutool.core.util.IdUtil;
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
     * 自定义 Token 生成策略
     * 参考 https://sa-token.cc/doc.html#/up/token-style?id=%e8%87%aa%e5%ae%9a%e4%b9%89-token-%e7%94%9f%e6%88%90%e7%ad%96%e7%95%a5
     */
    @Bean
    public void rewriteSaStrategy() {
        // 重写 Token 生成策略
        SaStrategy.me.createToken = (loginId, loginType) -> {
            // eg: Bearer B:2:1760202129674522624
            return loginId + ":" + IdUtil.getSnowflakeNextId();
        };
    }

}
