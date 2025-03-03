package com.zhengqing.common.netty.server.handler;

import com.zhengqing.common.netty.enums.NettyMsgCmdType;
import com.zhengqing.common.netty.enums.NettyTerminalType;
import com.zhengqing.common.netty.model.NettyMsgBase;
import com.zhengqing.common.netty.util.NettyChannelAttrKeyUtil;
import com.zhengqing.common.netty.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p> 心跳连接 扑通扑通... </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/2/26 18:15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HeartbeatHandler extends AbstractMsgHandler<String> {

    @Override
    NettyMsgCmdType getMsgCmdEnum() {
        return NettyMsgCmdType.HEART_BEAT;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, String beatInfo) {
        Long heartbeatTimes = NettyChannelAttrKeyUtil.getAttr(ctx.channel(), NettyChannelAttrKeyUtil.HEARTBEAT_TIMES);
        if (heartbeatTimes == null) {
            log.warn("【netty】系统检测到存在未登录连接发送心跳");
            return;
        }

        NettyChannelAttrKeyUtil.setAttr(ctx.channel(), NettyChannelAttrKeyUtil.HEARTBEAT_TIMES, ++heartbeatTimes);
        if (heartbeatTimes % 2 == 0) {
            // 每心跳2次，用户在线状态续一次命 tips: 如果前端每5秒发送一次心跳，这里间隔2次，相当于每10秒续一次命。
            Long userId = NettyChannelAttrKeyUtil.getAttr(ctx.channel(), NettyChannelAttrKeyUtil.USER_ID);
            Integer terminal = NettyChannelAttrKeyUtil.getAttr(ctx.channel(), NettyChannelAttrKeyUtil.TERMINAL);
            NettyUtil.ONLINE_STATUS.add(userId, NettyTerminalType.getType(terminal));
        }

        ctx.channel().writeAndFlush(NettyMsgBase.builder().cmd(NettyMsgCmdType.HEART_BEAT).data("pong").build());
    }

}
