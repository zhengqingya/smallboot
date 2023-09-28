package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-支付方式-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsPayTypeEnum {

    /**
     * 微信
     */
    WX((byte) 1, "微信"),
    /**
     * 支付宝
     */
    ALI((byte) 2, "支付宝");

    /**
     * 支付方式
     */
    private final Byte payType;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsPayTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsPayTypeEnum.values()));
    }

    /**
     * 根据指定支付方式查找相应枚举
     */
    public static OmsPayTypeEnum getEnum(Byte payType) {
        for (OmsPayTypeEnum itemEnum : LIST) {
            if (itemEnum.getPayType().equals(payType)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定支付方式！");
    }

}
