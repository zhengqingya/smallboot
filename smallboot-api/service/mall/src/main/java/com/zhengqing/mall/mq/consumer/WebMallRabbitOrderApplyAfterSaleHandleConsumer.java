package com.zhengqing.mall.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.mall.common.model.bo.OmsOrderAfterSaleCloseBO;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.service.WebOmsOrderAfterSaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * <p>
 * 买家发起售后申请？毫秒后，卖家未处理，自动关闭
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 16:26
 */
@Slf4j
@Component
public class WebMallRabbitOrderApplyAfterSaleHandleConsumer {

    @Resource
    private WebOmsOrderAfterSaleService webOmsOrderAfterSaleService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = {MallRabbitMqConstant.OMS_ORDER_APPLY_AFTER_SALE_HANDLE_QUEUE})
    public void onMessage(OmsOrderAfterSaleCloseBO params) {
        try {
            TenantIdContext.setTenantId(params.getTenantId());
            this.webOmsOrderAfterSaleService.unHandleAutoClose(params);
        } catch (Exception e) {
            log.error("[商城] 买家发起售后申请？毫秒后，卖家未处理，自动关闭处理参数：[{}] 错误信息：{}", JSON.toJSONString(params), e);
            throw e;
        }
    }

}
