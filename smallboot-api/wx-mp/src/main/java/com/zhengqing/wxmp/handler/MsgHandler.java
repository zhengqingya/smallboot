package com.zhengqing.wxmp.handler;

import cn.hutool.json.JSONUtil;
import com.zhengqing.wxmp.enums.WxMpAutoReplyTypeEnum;
import com.zhengqing.wxmp.model.dto.WxMpReplyMsgDTO;
import com.zhengqing.wxmp.service.IWxMpMsgReplyService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * <p> 消息处理 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/3/20 18:26
 */
@Component
@RequiredArgsConstructor
public class MsgHandler extends AbstractHandler {

    private final IWxMpMsgReplyService wxMpMsgReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        // 公众号appId
        String appId = WxMpConfigStorageHolder.get();

        // 消息回复
        this.wxMpMsgReplyService.replyMsg(WxMpReplyMsgDTO.builder()
                .appId(appId)
                .fromUser(wxMessage.getFromUser())
                .type(WxMpAutoReplyTypeEnum.关键词回复.getType())
                .content(wxMessage.getContent())
                .build());

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                    && weixinService.getKefuService().kfOnlineList()
                    .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //TODO 组装回复消息
        String content = "收到信息内容：" + JSONUtil.toJsonStr(wxMessage);

//        return new TextBuilder().build(content, wxMessage, weixinService);
        return null;
    }

}
