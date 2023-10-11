package com.zhengqing.common.sdk.douyin.mini.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p> 抖音 生成token 响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DyMiniAccessTokenVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private String errNo;

    /**
     * 错误信息
     */
    private String err_tips;

    private Data data;


    @lombok.Data
    public static class Data {
        /**
         * 获取的 access_token
         */
        private String access_token;

        /**
         * access_token 有效时间，单位：秒
         */
        private Long expires_in;
    }

}
