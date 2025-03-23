package com.zhengqing.common.netty.constant;

import com.zhengqing.common.base.constant.BaseConstant;

/**
 * <p> 全局常用变量 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/2/26 11:46
 */
public interface NettyRedisConstant {

    /**
     * 心跳超时时间 20s
     */
    long HEARTBEAT_TIMEOUT_SECOND = 20;

    /**
     * 服务端最大id,从0开始递增
     */
    String MAX_SERVER_ID = BaseConstant.BASE_PREFIX + ":im:max_server_id";

    /**
     * 用户ID所连接的服务端ID
     */
    String USER_RE_SERVER_ID = BaseConstant.BASE_PREFIX + ":im:user:server_id";

}
