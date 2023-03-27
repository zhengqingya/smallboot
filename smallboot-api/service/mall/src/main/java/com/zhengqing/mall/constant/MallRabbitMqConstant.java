package com.zhengqing.mall.constant;

import com.zhengqing.common.mq.constant.BaseRabbitMqConstant;

/**
 * <p> mq常量 - 商城 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/15 13:48
 */
public interface MallRabbitMqConstant extends BaseRabbitMqConstant {

    /**
     * 商城交换机
     */
    String MALL_EXCHANGE = "ex_mall_event";

    /**
     * 支付
     */
    String OMS_ORDER_PAY_ROUTING_KEY = "oms_order_pay_routing_key";
    String OMS_ORDER_PAY_QUEUE = "oms_order_pay";

    /**
     * 退款
     */
    String OMS_ORDER_REFUND_ROUTING_KEY = "oms_order_refund_routing_key";
    String OMS_ORDER_REFUND_QUEUE = "oms_order_refund";

    // ******************************************************************************

    /**
     * 延时交换机 - mq插件方式'rabbitmq-delayed-message-exchange'
     */
    String MALL_EVENT_DELAY_EXCHANGE = "ex_mall_event_delay";

    /**
     * 交换机类型-延时交换机
     */
    String EXCHANGE_DELAY = "x-delayed-message";

    /**
     * 订单未支付-自动取消-延时队列
     */
    String OMS_ORDER_UN_PAY_AUTO_CLOSE_ROUTING_KEY = "oms_order_un_pay_auto_close_routing_key";
    String OMS_ORDER_UN_PAY_AUTO_CLOSE_QUEUE = "oms_order_un_pay_auto_close";

    /**
     * 发货后？毫秒后自动确认收货-延时队列
     */
    String OMS_ORDER_AUTO_CONFIRM_RECEIPT_ROUTING_KEY = "oms_order_auto_confirm_receipt_routing_key";
    String OMS_ORDER_AUTO_CONFIRM_RECEIPT_QUEUE = "oms_order_auto_confirm_receipt";

    /**
     * 买家发起售后申请？毫秒后，卖家未处理，自动关闭-延时队列
     */
    String OMS_ORDER_APPLY_AFTER_SALE_HANDLE_ROUTING_KEY = "oms_order_apply_after_sale_handle_routing_key";

    String OMS_ORDER_APPLY_AFTER_SALE_HANDLE_QUEUE = "oms_order_apply_after_sale_handle";

    /**
     * 待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭-延时队列
     */
    String OMS_ORDER_AFTER_SALE_BUYER_DELIVER_ROUTING_KEY = "oms_order_after_sale_buyer_deliver_routing_key";
    String OMS_ORDER_AFTER_SALE_BUYER_DELIVER_QUEUE = "oms_order_after_sale_buyer_deliver";

    /**
     * 商品预售-延时队列
     */
    String PMS_SPU_PRESELL_ROUTING_KEY = "pms_spu_presell_routing_key";
    String PMS_SPU_PRESELL_QUEUE = "pms_spu_presell";

}
