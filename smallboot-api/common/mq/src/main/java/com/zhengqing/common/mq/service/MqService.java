package com.zhengqing.common.mq.service;


/**
 * <p> mq 服务类  </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/8 11:47
 */
public interface MqService {

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
    void send(String exchange, String routingKey, Object msgData);

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
    void sendDelay(String exchange, String routingKey, Object msgData, long delayMillisecond);

}

