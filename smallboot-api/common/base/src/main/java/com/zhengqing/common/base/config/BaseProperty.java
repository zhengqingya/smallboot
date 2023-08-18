package com.zhengqing.common.base.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 基础配置参数 </p>
 *
 * @author zhengqing
 * @description
 * @date 2020/8/15 16:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Configuration
@ConfigurationProperties(prefix = "smallboot")
public class BaseProperty extends CommonProperty {


}
