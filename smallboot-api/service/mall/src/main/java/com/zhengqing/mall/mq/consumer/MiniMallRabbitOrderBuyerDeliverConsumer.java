package com.zhengqing.mall.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.model.bo.OmsOrderAfterSaleCloseBO;
import com.zhengqing.mall.service.IOmsOrderAfterSaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 16:26
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MiniMallRabbitOrderBuyerDeliverConsumer {

    private final IOmsOrderAfterSaleService iOmsOrderAfterSaleService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = {MallRabbitMqConstant.OMS_ORDER_AFTER_SALE_BUYER_DELIVER_QUEUE})
    public void onMessage(OmsOrderAfterSaleCloseBO params) {
        try {
            TenantIdContext.setTenantId(params.getTenantId());
            this.iOmsOrderAfterSaleService.unHandleAutoClose(params);
        } catch (Exception e) {
            log.error("[商城] 待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭处理参数：[{}] 错误信息：{}", JSON.toJSONString(params), e);
            throw e;
        }
    }

}
