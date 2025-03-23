package com.zhengqing.system.config;

import com.zhengqing.common.base.config.CommonProperty;
import com.zhengqing.common.base.constant.BaseConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Configuration
@ConfigurationProperties(prefix = BaseConstant.YML_BASE_PREFIX)
public class SystemProperty extends CommonProperty {


    /**
     * 第三方授权参数
     */
    private ThirdpartOauth thirdpartOauth = new ThirdpartOauth();

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
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
