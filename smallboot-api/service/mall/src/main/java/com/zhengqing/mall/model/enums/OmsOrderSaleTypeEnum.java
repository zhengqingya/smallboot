package com.zhengqing.mall.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-售后类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderSaleTypeEnum {

    /**
     * 退款
     */
    REFUND((byte) 1, "退款"),
    /**
     * 退款退货
     */
    SALE_RETURN_AND_REFUND((byte) 2, "退款退货");
    /**
     * 换货
     */
//    EXCHANGE((byte) 3, "换货");
    /**
     * 退货
     */
//    SALE_RETURN((byte) 4, "退货");

    /**
     * 售后类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderSaleTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderSaleTypeEnum.values()));
    }

    /**
     * 根据指定类型查找相应枚举
     */
    public static OmsOrderSaleTypeEnum getEnum(Byte type) {
        for (OmsOrderSaleTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定售后类型！");
    }


}
