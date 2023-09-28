package com.zhengqing.mall.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * <p> 商城-订单-商品详情状态-枚举类 </p>
 *
 * @author zhengqingya
 * @description {@link OmsOrderStatusEnum}
 * 1->待支付 2->已取消 3->未发货 4->已发货 5->已完成
 * @date 2021/8/30 16:59
 */
@Getter
@AllArgsConstructor
public enum OmsOrderItemStatusEnum {

    /**
     * 待支付
     */
    UN_PAY((byte) 1, "待支付"),
    /**
     * 已取消
     */
    CANC((byte) 2, "已取消"),
    /**
     * 未发货
     */
    UN_BILL((byte) 3, "未发货"),
    /**
     * 已发货
     */
    BILL((byte) 4, "已发货"),
    /**
     * 已完成 (tips:虚拟商品只能用户自己在小程序端领取之后才变更为已完成状态！)
     */
    FINISH((byte) 5, "已完成");
    /**
     * 退款中
     */
//    REFUND_ING((byte) 6, "退款中"),
    /**
     * 已退款
     */
//    REFUND_FINISH((byte) 7, "已退款"),
    /**
     * 退款驳回
     */
//    REFUND_REJECT((byte) 8, "退款驳回"),
    /**
     * 退款
     */
//    REFUND((byte) 9, "退款"),
    /**
     * 退款退货
     */
//    SALE_RETURN_AND_REFUND((byte) 10, "退款退货"),
    /**
     * 换货
     */
//    EXCHANGE((byte) 11, "换货");

    /**
     * 订单类型
     */
    private final Byte status;
    /**
     * 描述
     */
    private final String desc;

    private static final List<OmsOrderItemStatusEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(OmsOrderItemStatusEnum.values()));
    }

    /**
     * 根据指定状态查找相应枚举
     */
    public static OmsOrderItemStatusEnum getEnum(Byte orderStatus) {
        for (OmsOrderItemStatusEnum itemEnum : LIST) {
            if (itemEnum.getStatus().equals(orderStatus)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定订单关联商品状态！");
    }


}
