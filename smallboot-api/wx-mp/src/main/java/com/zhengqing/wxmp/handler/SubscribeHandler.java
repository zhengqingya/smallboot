package com.zhengqing.wxmp.handler;

import com.zhengqing.wxmp.enums.WxMpAutoReplyTypeEnum;
import com.zhengqing.wxmp.model.dto.WxMpReplyMsgDTO;
import com.zhengqing.wxmp.service.IWxMpMsgReplyService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p> 关注处理 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/3/20 18:26
 */
@Component
@RequiredArgsConstructor
public class SubscribeHandler extends AbstractHandler {

    private final IWxMpMsgReplyService wxMpMsgReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 公众号appId
        String appId = WxMpConfigStorageHolder.get();

        // 消息回复
        this.wxMpMsgReplyService.replyMsg(WxMpReplyMsgDTO.builder()
                .appId(appId)
                .fromUser(wxMessage.getFromUser())
                .type(WxMpAutoReplyTypeEnum.关注时回复.getType())
                // subscribe
                .content(wxMessage.getEvent())
                .build());

        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService()
                    .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                // TODO 可以添加关注用户到本地数据库
            }
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                this.logger.info("该公众号没有获取用户信息权限！");
            }
        }


        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
//            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {
        //TODO
        return null;
    }

}
