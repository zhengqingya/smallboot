package com.zhengqing.mall.common.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-配送状态-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderShippingStatusEnum {

    /**
     * 实物
     */
    REAL((byte) 1, "实物"),
    /**
     * 虚拟
     */
    VIRTUAL((byte) 2, "虚拟");

    /**
     * 0.0
     * 类型
     */
    private final Byte shippingStatus;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderShippingStatusEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderShippingStatusEnum.values()));
    }

    /**
     * 根据指定配送状态查找相应枚举
     */
    public static OmsOrderShippingStatusEnum getEnum(Byte shippingStatus) {
        for (OmsOrderShippingStatusEnum itemEnum : LIST) {
            if (itemEnum.getShippingStatus().equals(shippingStatus)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定配送状态！");
    }

}
