package com.zhengqing.ums.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p> 微信小程序配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/12/7 16:40
 */
@Configuration
@EnableConfigurationProperties(WxMaProperty.class)
public class WxMaConfig {
    @Bean
    public WxMaService wxMaService(WxMaProperty properties) {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(properties.getAppid());
        config.setSecret(properties.getSecret());
        config.setMsgDataFormat(properties.getMsgDataFormat());

        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}