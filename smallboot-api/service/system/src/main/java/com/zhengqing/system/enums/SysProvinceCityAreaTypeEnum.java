package com.zhengqing.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

}
