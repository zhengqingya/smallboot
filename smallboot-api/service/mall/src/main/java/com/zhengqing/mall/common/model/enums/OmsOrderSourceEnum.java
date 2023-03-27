package com.zhengqing.mall.common.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单来源-枚举类 </p>
 *
 * @author zhengqingya
 * @description 1->微信小程序 2->支付宝小程序
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderSourceEnum {

    /**
     * 微信小程序
     */
    WX((byte) 1, "微信小程序"),
    /**
     * 支付宝小程序
     */
    ALI((byte) 2, "支付宝小程序");

    /**
     * 来源
     */
    private final Byte source;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderSourceEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderSourceEnum.values()));
    }

    /**
     * 根据指定订单来源查找相应枚举
     */
    public static OmsOrderSourceEnum getEnum(Byte source) {
        for (OmsOrderSourceEnum itemEnum : LIST) {
            if (itemEnum.getSource().equals(source)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定订单来源！");
    }

}
