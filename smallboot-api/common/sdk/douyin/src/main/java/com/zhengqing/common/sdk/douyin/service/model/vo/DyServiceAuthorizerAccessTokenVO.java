package com.zhengqing.common.sdk.douyin.service.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p> 抖音 授权小程序接口调用凭据 响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DyServiceAuthorizerAccessTokenVO {

    /**
     * 授权小程序接口调用凭据
     */
    private String authorizer_access_token;

    /**
     * authorizer_access_token 的有效期，单位：秒
     */
    private Long expires_in;

    /**
     * 刷新令牌，用于刷新已授权小程序的 authorizer_access_token
     */
    private String authorizer_refresh_token;

    /**
     * authorizer_refresh_token 的有效期，单位：秒
     */
    private Long refresh_expires_in;

    /**
     * 授权小程序 appid
     */
    private String authorizer_appid;

    /**
     * 授权小程序在授权跳转页勾选的权限
     */
    private List<AuthPerm> authorize_permission;

    @Data
    public static class AuthPerm {
        /**
         * 权限id
         */
        private Long id;
        /**
         * 权限名称
         */
        private String category;
        /**
         * 权限描述
         */
        private String description;
    }

}
