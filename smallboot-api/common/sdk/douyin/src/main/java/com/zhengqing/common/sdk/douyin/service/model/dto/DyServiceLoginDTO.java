package com.zhengqing.common.sdk.douyin.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p> 抖音 登录 请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DyServiceLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方小程序应用 appid
     */
    private String component_appid;
    /**
     * 授权小程序接口调用凭据
     */
    private String authorizer_access_token;

    /**
     * code 和 anonymous_code 至少要有一个
     */
    private String code;
    private String anonymous_code;

}
