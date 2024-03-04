package com.zhengqing.common.netty.server.ws;

import com.zhengqing.common.auth.util.AuthUtil;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> Sec-Websocket-Protocol 解析处理 </p>
 *
 * @author zhengqingya
 * @description 最后得返回同样的 Sec-Websocket-Protocol 响应才行，不然会断开连接
 * @date 2024/2/26 10:11
 */
@Slf4j
public class NettyHeaderHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            HttpRequest request = (FullHttpRequest) msg;
            HttpHeaders headers = request.headers();
            String token = headers.get("Sec-Websocket-Protocol");
            JwtUserBO jwtUserBO = AuthUtil.getLoginUser(token);
            log.info("用户信息：{}", jwtUserBO);
            JwtUserContext.set(jwtUserBO);
        } else {
            log.info("xxxx: {}", msg);
        }
        ctx.fireChannelRead(msg);
    }

}