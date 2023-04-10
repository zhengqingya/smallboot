package com.zhengqing.ums.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * <p> 微信小程序配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/12/7 16:40
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMaProperty {
    /**
     * 设置微信小程序的appid
     */
    private String appid;

    /**
     * 设置微信小程序的Secret
     */
    private String secret;

    /**
     * 消息格式，XML或者JSON
     */
    private String msgDataFormat;
}