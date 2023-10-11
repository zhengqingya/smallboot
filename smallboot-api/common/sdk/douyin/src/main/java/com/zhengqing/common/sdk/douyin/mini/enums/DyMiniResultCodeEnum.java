package com.zhengqing.common.sdk.douyin.mini.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 抖音 状态码 枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 16:47
 */
@Getter
@AllArgsConstructor
public enum DyMiniResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(0L, "成功");

    /**
     * 状态码
     */
    private final Long code;
    /**
     * 描述
     */
    private final String desc;

}
