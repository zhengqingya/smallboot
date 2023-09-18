package com.zhengqing.mall.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.service.IOmsOrderAfterSaleService;
import com.zhengqing.pay.model.bo.PayOrderNotifyBO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 微信退款成功回调处理
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 16:26
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebMallRabbitOrderRefundOkConsumer {

    private final IOmsOrderAfterSaleService iOmsOrderAfterSaleService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = {MallRabbitMqConstant.OMS_ORDER_REFUND_QUEUE})
    public void onMessage(PayOrderNotifyBO payOrderNotifyBO) {
        try {
            TenantIdContext.setTenantId(payOrderNotifyBO.getTenantId());
            this.iOmsOrderAfterSaleService.refundSuccessCallback(payOrderNotifyBO);
        } catch (Exception e) {
            log.error("[商城] 微信退款成功回调处理参数：[{}] 错误信息：{}", JSON.toJSONString(payOrderNotifyBO), e);
            throw e;
        }
    }

}
