package com.zhengqing.common.sdk.douyin.mini.model.dto;

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
public class DyMiniLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 小程序 ID
     */
    private String appid;
    /**
     * 小程序的 APP Secret，可以在开发者后台获取
     */
    private String secret;

    /**
     * login 接口返回的登录凭证
     */
    private String code;
    /**
     * login 接口返回的匿名登录凭证
     */
    private String anonymous_code;

}
