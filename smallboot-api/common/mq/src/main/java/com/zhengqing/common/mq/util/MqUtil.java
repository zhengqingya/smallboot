package com.zhengqing.common.mq.util;

import com.zhengqing.common.mq.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * MQ 工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/11/27 14:38
 */
@Component
public class MqUtil {

    private static MqService mqService;

    @Autowired
    public MqUtil(MqService mqService) {
        MqUtil.mqService = mqService;
    }


    /**
     * 发送普通消息 （默认交换机）
     *
     * @param exchange   交换机
     * @param routingKey 路由key
     * @param msgData    消息
     * @return void
     * @author zhengqingya
     * @date 2022/7/8 12:47
     */
    public static void send(String exchange, String routingKey, Object msgData) {
        mqService.send(exchange, routingKey, msgData);
    }

    /**
     * 延迟队列
     *
     * @param exchange         交换机
     * @param routingKey       路由key
     * @param msgData          消息
     * @param delayMillisecond 消息延时时间 单位：毫秒
     * @return void
     * @author zhengqingya
     * @date 2022/7/8 12:40
     */
    public static void sendDelay(String exchange, String routingKey, Object msgData, long delayMillisecond) {
        mqService.sendDelay(exchange, routingKey, msgData, delayMillisecond);
    }

}
