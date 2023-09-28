package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-发货方式-枚举类 </p>
 *
 * @author zhengqingya
 * @description 1->快递 2->-
 * @date 2021/8/30 8:58 下午
 */
@Getter
@AllArgsConstructor
public enum OmsOrderDeliverTypeEnum {

    /**
     * 快递
     */
    EXPRESS((byte) 1, "快递"),
    /**
     * 无需发货
     */
    NULL((byte) 2, "-");

    /**
     * 类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderDeliverTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderDeliverTypeEnum.values()));
    }

    /**
     * 根据指定订单发货方式查找相应枚举
     */
    public static OmsOrderDeliverTypeEnum getEnum(Byte type) {
        for (OmsOrderDeliverTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定订单发货方式！");
    }

}
