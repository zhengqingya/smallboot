package com.zhengqing.mall.common.model.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-售后时的商品状态-枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/24 17:24
 */
@Getter
@AllArgsConstructor
public enum OmsOrderAfterSaleSpuStatusEnum {

    /**
     * 未发货
     */
    UN_BILL((byte) 1, "未发货"),
    /**
     * 未收到货
     */
    UN_RECEIVE((byte) 2, "未收到货"),
    /**
     * 收到货
     */
    RECEIVE((byte) 3, "收到货");

    /**
     * 状态
     */
    private final Byte status;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderAfterSaleSpuStatusEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderAfterSaleSpuStatusEnum.values()));
    }

    /**
     * 根据指定状态查找相应枚举
     */
    public static OmsOrderAfterSaleSpuStatusEnum getEnum(Byte status) {
        for (OmsOrderAfterSaleSpuStatusEnum itemEnum : LIST) {
            if (itemEnum.getStatus().equals(status)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定售后商品状态！");
    }

    /**
     * 根据指定订单商品状态查找相应售后枚举
     */
    public static OmsOrderAfterSaleSpuStatusEnum getEnumByOrderStatus(OmsOrderStatusEnum orderStatusEnum) {
        switch (orderStatusEnum) {
            case UN_BILL:
                return OmsOrderAfterSaleSpuStatusEnum.UN_BILL;
            case BILL:
                return OmsOrderAfterSaleSpuStatusEnum.UN_RECEIVE;
            case FINISH:
            default:
                throw new MyException("未找到对应售后商品状态！");
        }
    }

}
