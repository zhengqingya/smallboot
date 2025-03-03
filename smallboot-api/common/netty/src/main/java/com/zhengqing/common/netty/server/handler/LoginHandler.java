package com.zhengqing.common.netty.server.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhengqing.common.auth.util.AuthUtil;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import com.zhengqing.common.netty.constant.NettyRedisConstant;
import com.zhengqing.common.netty.enums.NettyMsgCmdType;
import com.zhengqing.common.netty.enums.NettyTerminalType;
import com.zhengqing.common.netty.model.NettyLogin;
import com.zhengqing.common.netty.model.NettyMsgBase;
import com.zhengqing.common.netty.server.NettyServerRunner;
import com.zhengqing.common.netty.server.NettyUserCtxMap;
import com.zhengqing.common.netty.util.NettyChannelAttrKeyUtil;
import com.zhengqing.common.redis.util.RedisUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p> 登陆处理 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/2/26 18:15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHandler extends AbstractMsgHandler<NettyLogin> {

    @Value("${jwt.accessToken.secret}")
    private String accessTokenSecret;

    @Override
    NettyMsgCmdType getMsgCmdEnum() {
        return NettyMsgCmdType.LOGIN;
    }

    @Override
    public synchronized void handle(ChannelHandlerContext ctx, NettyLogin loginInfo) {
        JwtUserBO jwtUserBO = null;
        try {
            jwtUserBO = AuthUtil.getLoginUser(loginInfo.getAccessToken());
        } catch (Exception e) {
            log.warn("【netty】用户token：{} 校验不通过:{}，强制下线", loginInfo.getAccessToken(), e.getMessage());
            ctx.channel().writeAndFlush(NettyMsgBase.builder().cmd(NettyMsgCmdType.FORCE_LOGOUT).data("强制下线：" + e.getMessage()).build());
            ctx.channel().close();
            return;
        }
        JwtUserContext.set(jwtUserBO);

        Long userId = Long.valueOf(jwtUserBO.getUserId());
        Integer terminal = NettyTerminalType.WEB.getType();
        log.info("【netty】用户登录: {}", JSONUtil.toJsonStr(jwtUserBO));

        ChannelHandlerContext context = NettyUserCtxMap.getCtx(userId, terminal);
        if (context != null && !ctx.channel().id().equals(context.channel().id())) {
            // 不允许多地登录,强制下线
            context.channel().writeAndFlush(NettyMsgBase.builder().cmd(NettyMsgCmdType.FORCE_LOGOUT).data("您已在其他地方登陆，将被强制下线").build());
            log.info("【netty】异地登录，强制下线: {}", JSONUtil.toJsonStr(jwtUserBO));
        }

        // 绑定用户和channel
        NettyUserCtxMap.add(userId, terminal, ctx);

        // 设置用户id属性
        NettyChannelAttrKeyUtil.setAttr(ctx.channel(), NettyChannelAttrKeyUtil.USER_ID, userId);
        // 设置用户终端类型
        NettyChannelAttrKeyUtil.setAttr(ctx.channel(), NettyChannelAttrKeyUtil.TERMINAL, terminal);
        // 初始化心跳次数
        NettyChannelAttrKeyUtil.setAttr(ctx.channel(), NettyChannelAttrKeyUtil.HEARTBEAT_TIMES, 0L);

        /**
         * 记录每个user的channelId，没有心跳续期时自动过期
         * {@link HeartbeatHandler#handle(ChannelHandlerContext, String)
         */
        RedisUtil.setEx(StrUtil.format("{}:{}:{}", NettyRedisConstant.USER_RE_SERVER_ID, userId, terminal), NettyServerRunner.server_id, NettyRedisConstant.HEARTBEAT_TIMEOUT_SECOND, TimeUnit.SECONDS);

        ctx.channel().writeAndFlush(NettyMsgBase.builder().cmd(NettyMsgCmdType.LOGIN).data("登陆成功").build());
    }

    @Override
    public NettyLogin transform(Object data) {
        return super.transform(data, this.bodyClass);
    }

}
