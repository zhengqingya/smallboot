package com.zhengqing.common.mq.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.zhengqing.common.mq.service.MqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> rabbitmq 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/8 11:49
 */
@Slf4j
@Service
public class RabbitMqServiceImpl implements MqService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String exchange, String routingKey, Object msgData) {
        log.info("[MQ生产者] 交换机:[{}] 路由key:[{}] 发送消息:[{}]", exchange, routingKey, JSONUtil.toJsonStr(msgData));
        this.rabbitTemplate.convertAndSend(exchange, routingKey, msgData);
    }

    @Override
    public void sendDelay(String exchange, String routingKey, Object msgData, long delayMillisecond) {
        log.info("[MQ生产者] 交换机:[{}] 路由key:[{}] 发送消息:[{}] 延时[{}]毫秒", exchange, routingKey, JSONUtil.toJsonStr(msgData), delayMillisecond);
        Assert.isTrue(delayMillisecond <= 4294967295L, "MQ最大延时4294967295毫秒(即49.7103天)");
        this.rabbitTemplate.convertAndSend(exchange, routingKey, msgData, message -> {
            message.getMessageProperties().setHeader("x-delay", delayMillisecond);
            return message;
        });
    }

}

