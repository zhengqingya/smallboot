package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单状态-枚举类 </p>
 *
 * @author zhengqingya
 * @description {@link OmsOrderItemStatusEnum}
 * 1->待支付 2->已取消 3->待发货 4->待收货 5->已完成 6->已退款
 * @date 2021/8/30 16:59
 */
@Getter
@AllArgsConstructor
public enum OmsOrderStatusEnum {

    /**
     * 待支付
     */
    UN_PAY((byte) 1, "待支付"),
    /**
     * 已取消
     */
    CANC((byte) 2, "已取消"),
    /**
     * 待发货
     */
    UN_BILL((byte) 3, "待发货"),
    /**
     * 待收货
     */
    BILL((byte) 4, "待收货"),
    /**
     * 已完成
     */
    FINISH((byte) 5, "已完成"),
    /**
     * 已退款
     */
    REFUND((byte) 6, "已退款");

    /**
     * 状态
     */
    private final Byte status;
    /**
     * 描述
     */
    private final String desc;

    public static final List<OmsOrderStatusEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderStatusEnum.values()));
    }

    /**
     * 根据指定订单状态查找相应枚举
     */
    public static OmsOrderStatusEnum getEnum(Byte orderStatus) {
        for (OmsOrderStatusEnum itemEnum : LIST) {
            if (itemEnum.getStatus().equals(orderStatus)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定订单状态！");
    }


}
