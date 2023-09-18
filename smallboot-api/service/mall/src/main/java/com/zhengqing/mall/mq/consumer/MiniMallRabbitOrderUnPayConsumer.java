package com.zhengqing.mall.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.model.dto.MiniOmsOrderUnPayDTO;
import com.zhengqing.mall.service.IOmsOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 支付未支付回调处理
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 16:26
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MiniMallRabbitOrderUnPayConsumer {

    private final IOmsOrderService miniIOmsOrderService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = {MallRabbitMqConstant.OMS_ORDER_UN_PAY_AUTO_CLOSE_QUEUE})
    public void onMessage(MiniOmsOrderUnPayDTO params) {
        try {
            TenantIdContext.setTenantId(params.getTenantId());
            this.miniIOmsOrderService.unPayCallback(params);
        } catch (Exception e) {
            log.error("[商城] 支付未支付回调处理参数：[{}] 错误信息：{}", JSON.toJSONString(params), e);
            throw e;
        }
    }

}
