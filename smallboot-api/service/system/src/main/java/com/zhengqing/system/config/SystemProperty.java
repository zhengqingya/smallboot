package com.zhengqing.system.config;

import com.zhengqing.common.base.config.CommonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zhyd.oauth.config.AuthConfig;
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
public class SystemProperty extends CommonProperty {

    /**
     * 第三方授权参数
     */
    private final ThirdpartOauth thirdpartOauth = new ThirdpartOauth();

    @Data
    public static class ThirdpartOauth {
        private String redirectUrlPrefix;
        private String webRedirectUrl;
        private String webBindRedirectUrl;
        private AuthConfig gitee;
        private AuthConfig github;
        private AuthConfig qq;
        private AuthConfig giteeBind;
        private AuthConfig githubBind;
    }

}
