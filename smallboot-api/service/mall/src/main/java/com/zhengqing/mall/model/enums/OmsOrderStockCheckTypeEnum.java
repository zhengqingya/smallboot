package com.zhengqing.mall.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单库存校验类型-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderStockCheckTypeEnum {

    /**
     * 下单扣减库存校验
     */
    CREATE_ORDER((byte) 1, "下单校验"),
    /**
     * 支付扣减库存校验
     */
    PAY((byte) 2, "支付校验");

    /**
     * 类型
     */
    private final Byte type;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderStockCheckTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderStockCheckTypeEnum.values()));
    }

    /**
     * 根据指定类型查找相应枚举
     */
    public static OmsOrderStockCheckTypeEnum getEnum(Byte type) {
        for (OmsOrderStockCheckTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定订单库存校验类型！");
    }

}
