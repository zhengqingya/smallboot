package com.zhengqing.common.netty.util;

import cn.hutool.core.util.StrUtil;
import com.zhengqing.common.netty.constant.NettyRedisConstant;
import com.zhengqing.common.netty.enums.NettyMsgCmdType;
import com.zhengqing.common.netty.enums.NettyTerminalType;
import com.zhengqing.common.netty.enums.NettyUserLogoutMsgEnum;
import com.zhengqing.common.netty.model.NettyLogin;
import com.zhengqing.common.netty.model.NettyMsgBase;
import com.zhengqing.common.netty.server.NettyServerRunner;
import com.zhengqing.common.netty.server.NettyUserCtxMap;
import com.zhengqing.common.netty.server.handler.HeartbeatHandler;
import com.zhengqing.common.netty.server.handler.LoginHandler;
import com.zhengqing.common.redis.util.RedisUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>netty工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/2/27 14:09
 */
@Slf4j
public class NettyUtil {

    /**
     * 在线状态 & 心跳续期
     */
    public static class ONLINE_STATUS {
        public static void add(Long userId, NettyTerminalType terminal) {
            /**
             * 记录每个user的channelId，没有心跳续期时自动过期
             * {@link HeartbeatHandler#handle(ChannelHandlerContext, String)
             */
            RedisUtil.setEx(getKey(userId, terminal), NettyServerRunner.server_id, NettyRedisConstant.HEARTBEAT_TIMEOUT_SECOND, TimeUnit.SECONDS);
//        RedisUtil.expire(getKey(userId, terminal), NettyRedisConstant.HEARTBEAT_TIMEOUT_SECOND, TimeUnit.SECONDS);
        }

        public static void delete(Long userId, NettyTerminalType terminal) {
            // 用户下线
            RedisUtil.delete(getKey(userId, terminal));
        }

        public static Map<Long, Boolean> batchGet(List<Long> userIdList) {
            Map<Long, Boolean> result = new HashMap<>();
            userIdList.forEach(userId -> {
                /**
                 * 暂时只考虑web端，因为登录时默认赋值web端
                 * {@link LoginHandler#handle(ChannelHandlerContext, NettyLogin)}
                 */
                String val = RedisUtil.get(getKey(userId, NettyTerminalType.WEB));
                result.put(userId, StrUtil.isNotBlank(val));
            });
            return result;
        }

        private static String getKey(Long userId, NettyTerminalType terminal) {
            return StrUtil.format("{}:{}:{}", NettyRedisConstant.USER_RE_SERVER_ID, userId, terminal);
        }
    }

    /**
     * 发送消息
     */
    public static class SEND_MSG {
        public static void logout(Long userId, NettyUserLogoutMsgEnum msgEnum) {
            ChannelHandlerContext ctx = NettyUserCtxMap.getCtx(userId, NettyTerminalType.WEB.getType());
            if (ctx != null) {
                ctx.channel().writeAndFlush(NettyMsgBase.builder().cmd(NettyMsgCmdType.FORCE_LOGOUT).data("强制下线：" + msgEnum.getDesc()).build());
                log.info("【netty】userId:{} 强制下线: {}", userId, msgEnum.getDesc());
            }
        }
    }
}
