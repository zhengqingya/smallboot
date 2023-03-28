package com.zhengqing.mall.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.model.dto.MiniOmsOrderConfirmReceiptDTO;
import com.zhengqing.mall.service.WebOmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * <p>
 * 发货后？毫秒后自动确认收货
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 16:26
 */
@Slf4j
@Component
public class WebMallRabbitAutoConfirmReceiptConsumer {

    @Resource
    private WebOmsOrderService webOmsOrderService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = {MallRabbitMqConstant.OMS_ORDER_AUTO_CONFIRM_RECEIPT_QUEUE})
    public void onMessage(MiniOmsOrderConfirmReceiptDTO params) {
        try {
            TenantIdContext.setTenantId(params.getTenantId());
            this.webOmsOrderService.confirmReceipt(params);
        } catch (Exception e) {
            log.error("[商城] 发货后？毫秒后自动确认收货处理参数：[{}] 错误信息：{}", JSON.toJSONString(params), e);
            throw e;
        }
    }

}
