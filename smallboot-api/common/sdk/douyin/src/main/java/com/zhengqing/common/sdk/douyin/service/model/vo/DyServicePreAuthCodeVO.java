package com.zhengqing.common.sdk.douyin.service.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 抖音 预授权码 响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DyServicePreAuthCodeVO {

    /**
     * 预授权码
     */
    private String pre_auth_code;

    /**
     * 预授权码有效期，单位：秒
     */
    private Long expires_in;

}
