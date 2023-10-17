package com.zhengqing.system.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 小程序类型枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum SysAppTypeEnum {

    共享小程序(1, "共享版"),
    独立小程序(2, "独立版");

    private final Integer type;
    private final String desc;

    private static final List<SysAppTypeEnum> LIST = Arrays.asList(SysAppTypeEnum.values());

    public static SysAppTypeEnum getEnum(Integer type) {
        for (SysAppTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的小程序类型！");
    }

}
