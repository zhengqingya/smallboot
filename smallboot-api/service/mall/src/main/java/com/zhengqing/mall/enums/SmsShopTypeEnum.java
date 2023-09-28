package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 店铺类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/25 10:03 下午
 */
@Getter
@AllArgsConstructor
public enum SmsShopTypeEnum {

    餐饮(1, "餐饮"),
    电商(2, "电商"),
    教育(3, "教育");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<SmsShopTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(SmsShopTypeEnum.values()));
    }

    public static SmsShopTypeEnum getEnum(Byte type) {
        for (SmsShopTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定店铺类型！");
    }

}
