package com.zhengqing.mall.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.mall.common.model.dto.PmsSpuPresellDTO;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.service.MiniPmsSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * <p>
 * 商品预售回调处理
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 16:26
 */
@Slf4j
@Component
public class MiniMallRabbitSpuPresellConsumer {

    @Resource
    private MiniPmsSpuService miniPmsSpuService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = {MallRabbitMqConstant.PMS_SPU_PRESELL_QUEUE})
    public void onMessage(PmsSpuPresellDTO params) {
        try {
            TenantIdContext.setTenantId(params.getTenantId());
            this.miniPmsSpuService.presellRemindToSendUser(params);
        } catch (Exception e) {
            log.error("[商城] 商品预售回调处理参数：[{}] 错误信息：{}", JSON.toJSONString(params), e);
            throw e;
        }
    }

}
