package com.zhengqing.system.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 系统-系统配置类型枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum SysConfigTypeEnum {

    配置(1, "配置"),
    属性(2, "属性");

    private final Integer type;
    private final String desc;

    private static final List<SysConfigTypeEnum> LIST = Arrays.asList(SysConfigTypeEnum.values());

    public static SysConfigTypeEnum getEnum(Integer type) {
        for (SysConfigTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的系统配置类型！");
    }

}
