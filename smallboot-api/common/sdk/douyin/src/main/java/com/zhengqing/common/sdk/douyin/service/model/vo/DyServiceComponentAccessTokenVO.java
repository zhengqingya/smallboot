package com.zhengqing.common.sdk.douyin.service.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 抖音 第三方小程序接口调用凭据 响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DyServiceComponentAccessTokenVO {

    /**
     * 第三方小程序应用接口调用凭据
     */
    private String component_access_token;

    /**
     * 有效期，单位：秒
     */
    private Long expires_in;

}
