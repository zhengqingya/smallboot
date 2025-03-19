package com.zhengqing.common.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * <p> Sa-Token 配置属性 </p>
 *
 * @author zhengqingya
 * @description 拦截/开放 URL
 * @date 2021/11/3 8:54 下午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sa-token", ignoreUnknownFields = true)
public class SaTokenProperty {

    /**
     * token名称 (同时也是cookie名称)  注意不能带冒号:
     */
    private String tokenName;

    /**
     * 拦截url
     */
    private List<String> interceptUrlList;

    /**
     * 开放url
     * FIXME：暂不支持restful接口放行
     */
    private List<String> openUrlList;

    /**
     * 开放url注解包
     */
    private List<String> openUrlAnnotationClassPathList;

}
