package com.zhengqing.system.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 系统-系统版本类型枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum SysVersionTypeEnum {

    抖音代开发小程序(1, "抖音代开发小程序"),
    微信代开发小程序(2, "微信代开发小程序");

    private final Integer type;
    private final String desc;

    private static final List<SysVersionTypeEnum> LIST = Arrays.asList(SysVersionTypeEnum.values());

    public static SysVersionTypeEnum getEnum(Integer type) {
        for (SysVersionTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的系统版本类型！");
    }

}
