package com.zhengqing.system.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 操作日志类型枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum SysLogTypeEnum {

    操作日志(1, "操作日志"),
    登录日志(2, "登录日志");

    private final Integer type;
    private final String desc;

    private static final List<SysLogTypeEnum> LIST = Arrays.asList(SysLogTypeEnum.values());

    public static SysLogTypeEnum getEnum(Integer type) {
        for (SysLogTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的日志类型！");
    }

}
