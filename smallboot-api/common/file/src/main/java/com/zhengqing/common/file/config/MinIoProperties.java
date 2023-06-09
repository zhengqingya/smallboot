package com.zhengqing.common.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * MinIO属性类
 * </p>
 *
 * @author zhengqing
 * @description
 * @date 2020/8/15 16:01
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIoProperties {

    /**
     * minio地址+端口号 -- 内网
     */
    private String url;

    /**
     * minio地址+端口号 -- 外网
     */
//    private String outUrl;

    /**
     * minio用户名
     */
    private String accessKey;

    /**
     * minio密码
     */
    private String secretKey;

    /**
     * 文件桶的名称
     */
    private String bucketName;

}
