package com.zhengqing.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 系统来源 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/13 20:25
 */
@Getter
@AllArgsConstructor
public enum SysSystemSourceEnum {

    当前系统("smallboot", "当前系统"),
    其它("other", "其它");

    private final String systemSource;
    private final String desc;

    private static final List<SysSystemSourceEnum> LIST = Arrays.asList(SysSystemSourceEnum.values());

    public static SysSystemSourceEnum getEnum(String systemSource) {
        for (SysSystemSourceEnum itemEnum : LIST) {
            if (itemEnum.getSystemSource().equals(systemSource)) {
                return itemEnum;
            }
        }
        return null;
    }

}
