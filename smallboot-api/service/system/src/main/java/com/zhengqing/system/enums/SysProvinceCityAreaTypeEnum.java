package com.zhengqing.system.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 省市区类型 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 17:16
 */
@Getter
@AllArgsConstructor
public enum SysProvinceCityAreaTypeEnum {

    PROVINCE(1),
    CITY(2),
    AREA(3);

    private final Integer type;

    private static final List<SysProvinceCityAreaTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(SysProvinceCityAreaTypeEnum.values()));
    }

    public static SysProvinceCityAreaTypeEnum getEnum(Integer type) {
        for (SysProvinceCityAreaTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的数据类型！");
    }

}
