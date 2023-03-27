package com.zhengqing.mall.config.rabbitmq;

import com.google.common.collect.Maps;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * <p> 商城-mq </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/15 14:22
 */
@Configuration
public class MallRabbitMqConfig {

    /**
     * 普通交换机
     */
    @Bean
    public DirectExchange mallEventExchange() {
        return new DirectExchange(MallRabbitMqConstant.MALL_EXCHANGE, true, false);
    }

    /**
     * 延时交换机
     */
    @Bean
    public CustomExchange mallEventDelayExchange() {
        Map<String, Object> args = Maps.newHashMap();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(MallRabbitMqConstant.MALL_EVENT_DELAY_EXCHANGE, "x-delayed-message", true, false, args);
    }

    // ============================= ↓↓↓↓↓↓ 订单支付成功 ↓↓↓↓↓↓ =============================

    @Bean
    public Queue orderPayQueue() {
        return new Queue(MallRabbitMqConstant.OMS_ORDER_PAY_QUEUE, true, false, false);
    }

    @Bean
    public Binding orderPayBinding() {
        return BindingBuilder.bind(this.orderPayQueue()).to(this.mallEventExchange()).with(MallRabbitMqConstant.OMS_ORDER_PAY_ROUTING_KEY);
    }

    // ============================= ↓↓↓↓↓↓ 订单退款成功 ↓↓↓↓↓↓ =============================

    @Bean
    public Queue orderRefundQueue() {
        return new Queue(MallRabbitMqConstant.OMS_ORDER_REFUND_QUEUE, true, false, false);
    }

    @Bean
    public Binding orderRefundBinding() {
        return BindingBuilder.bind(this.orderRefundQueue()).to(this.mallEventExchange()).with(MallRabbitMqConstant.OMS_ORDER_REFUND_ROUTING_KEY);
    }

    // ============================= ↓↓↓↓↓↓ 商品预售 ↓↓↓↓↓↓ =============================

    @Bean
    public Queue spuPresellQueue() {
        return new Queue(MallRabbitMqConstant.PMS_SPU_PRESELL_QUEUE, true, false, false);
    }

    @Bean
    public Binding spuPresellBinding() {
        return BindingBuilder.bind(this.spuPresellQueue())
                .to(this.mallEventDelayExchange())
                .with(MallRabbitMqConstant.PMS_SPU_PRESELL_ROUTING_KEY)
                .noargs();
    }

    // ============================= ↓↓↓↓↓↓ 订单未支付自动取消 ↓↓↓↓↓↓ =============================

    @Bean
    public Queue orderUnPayAutoCloseQueue() {
        return new Queue(MallRabbitMqConstant.OMS_ORDER_UN_PAY_AUTO_CLOSE_QUEUE, true);
    }

    @Bean
    public Binding orderUnPayAutoCloseBinding() {
        return BindingBuilder.bind(this.orderUnPayAutoCloseQueue())
                .to(this.mallEventDelayExchange())
                .with(MallRabbitMqConstant.OMS_ORDER_UN_PAY_AUTO_CLOSE_ROUTING_KEY)
                .noargs();
    }

    // ============================= ↓↓↓↓↓↓ 发货后？毫秒后自动确认收货 ↓↓↓↓↓↓ =============================

    @Bean
    public Queue orderAutoConfirmReceiptQueue() {
        return new Queue(MallRabbitMqConstant.OMS_ORDER_AUTO_CONFIRM_RECEIPT_QUEUE, true);
    }

    @Bean
    public Binding orderAutoConfirmReceiptBinding() {
        return BindingBuilder.bind(this.orderAutoConfirmReceiptQueue())
                .to(this.mallEventDelayExchange())
                .with(MallRabbitMqConstant.OMS_ORDER_AUTO_CONFIRM_RECEIPT_ROUTING_KEY)
                .noargs();
    }

    // ============================= ↓↓↓↓↓↓ 买家发起售后申请？毫秒后，卖家未处理，自动关闭 ↓↓↓↓↓↓ =============================

    @Bean
    public Queue orderApplyAfterSaleHandleQueue() {
        return new Queue(MallRabbitMqConstant.OMS_ORDER_APPLY_AFTER_SALE_HANDLE_QUEUE, true);
    }

    @Bean
    public Binding orderApplyAfterSaleHandleBinding() {
        return BindingBuilder.bind(this.orderApplyAfterSaleHandleQueue())
                .to(this.mallEventDelayExchange())
                .with(MallRabbitMqConstant.OMS_ORDER_APPLY_AFTER_SALE_HANDLE_ROUTING_KEY)
                .noargs();
    }

    // ============================= ↓↓↓↓↓↓ 待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭 ↓↓↓↓↓↓ =============================

    @Bean
    public Queue orderAfterSaleBuyerDeliverQueue() {
        return new Queue(MallRabbitMqConstant.OMS_ORDER_AFTER_SALE_BUYER_DELIVER_QUEUE, true);
    }

    @Bean
    public Binding orderAfterSaleBuyerDeliverBinding() {
        return BindingBuilder.bind(this.orderAfterSaleBuyerDeliverQueue())
                .to(this.mallEventDelayExchange())
                .with(MallRabbitMqConstant.OMS_ORDER_AFTER_SALE_BUYER_DELIVER_ROUTING_KEY)
                .noargs();
    }

}
