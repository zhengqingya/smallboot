package com.zhengqing.system.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统来源
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/13 20:25
 */
@Getter
@AllArgsConstructor
public enum SysSystemSourceEnum {

    小工具(0, "smallboot"),
    其它(1, "other");

    private final Integer systemSource;
    private final String desc;

    private static final List<SysSystemSourceEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(SysSystemSourceEnum.values()));
    }

    public static SysSystemSourceEnum getEnum(Integer systemSource) {
        for (SysSystemSourceEnum itemEnum : LIST) {
            if (itemEnum.getSystemSource().equals(systemSource)) {
                return itemEnum;
            }
        }
        return 小工具;
    }

}
