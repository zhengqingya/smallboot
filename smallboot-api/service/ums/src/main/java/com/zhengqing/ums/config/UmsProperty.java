package com.zhengqing.ums.config;

import com.zhengqing.common.core.config.CommonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 配置信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/19 9:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Configuration
@ConfigurationProperties(prefix = "smallboot")
public class UmsProperty extends CommonProperty {


}
