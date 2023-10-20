package com.zhengqing.common.sdk.douyin.mini.model.vo;

import com.zhengqing.common.sdk.douyin.mini.enums.DyMiniResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p> 抖音 生成client_token 响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DyMiniLoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码 {@link DyMiniResultCodeEnum}
     */
    private Long err_no;

    /**
     * 错误信息
     */
    private String err_tips;

    private Data data;

    @lombok.Data
    public static class Data {
        /**
         * 会话密钥，如果请求时有 code 参数才会返回
         * session_key 会随着login接口的调用被刷新。可以通过checkSession方法验证当前 session 是否有效，从而避免频繁登录。
         */
        private String session_key;
        /**
         * 用户在当前小程序的 ID，如果请求时有 code 参数才会返回
         */
        private String openid;
        /**
         * 匿名用户在当前小程序的 ID，如果请求时有 anonymous_code 参数才会返回
         */
        private String anonymous_openid;
        /**
         * 用户在小程序平台的唯一标识符，请求时有 code 参数才会返回。如果开发者拥有多个小程序，可通过 unionid 来区分用户的唯一性。
         */
        private String unionid;
    }

}
