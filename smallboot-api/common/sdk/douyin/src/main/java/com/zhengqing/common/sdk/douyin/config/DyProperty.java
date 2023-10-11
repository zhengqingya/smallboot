package com.zhengqing.common.sdk.douyin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 抖音配置信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "douyin")
public class DyProperty {
    /**
     * true:线上环境 false:沙盒环境
     */
    private Boolean isOnLine;

    /**
     * app
     */
    private App app;
    /**
     * 开放api
     */
    private OpenApi openApi;
    /**
     * 小程序
     */
    private MiniApp miniApp;

    @Data
    public static class App {
        /**
         * APPID
         */
        private String appId;
        /**
         * AppSecret
         */
        private String appSecret;
    }

    @Data
    public static class OpenApi {
        /**
         * 请求api前缀
         */
        private String apiPrefix;
    }


    @Data
    public static class MiniApp {
        /**
         * 请求api前缀 -- 线上地址
         */
        private String apiPrefix;
        /**
         * 请求api前缀 -- 沙盒地址
         */
        private String apiPrefixSandbox;
    }

}
