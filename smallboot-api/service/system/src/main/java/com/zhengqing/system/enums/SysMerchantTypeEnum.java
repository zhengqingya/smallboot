package com.zhengqing.system.enums;

import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 商户类型枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/19 9:50
 */
@Getter
@AllArgsConstructor
public enum SysMerchantTypeEnum {

    共享小程序(1, "配置"),
    独立小程序(2, "属性");

    private final Integer type;
    private final String desc;

    private static final List<SysMerchantTypeEnum> LIST = Arrays.asList(SysMerchantTypeEnum.values());

    public static SysMerchantTypeEnum getEnum(Integer type) {
        for (SysMerchantTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的商户类型！");
    }

}
