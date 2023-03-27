package com.zhengqing.wxmp.config;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p> WxMpService bean </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/3/20 19:28
 */
@Configuration
@RequiredArgsConstructor
public class WxMpServiceConfig {

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setMaxRetryTimes(3);
        return wxMpService;
    }

}
