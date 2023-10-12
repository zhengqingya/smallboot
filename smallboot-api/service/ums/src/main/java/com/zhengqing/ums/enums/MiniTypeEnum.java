package com.zhengqing.ums.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 小程序类型 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/13 20:25
 */
@Getter
@AllArgsConstructor
public enum MiniTypeEnum {

    微信小程序(1, "微信小程序"),
    抖音小程序(2, "抖音小程序");

    private final Integer type;
    private final String desc;

    private static final List<MiniTypeEnum> LIST = Arrays.asList(MiniTypeEnum.values());

    public static MiniTypeEnum getEnum(Integer type) {
        for (MiniTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的小程序类型！");
    }

}
