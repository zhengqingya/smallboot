package com.zhengqing.wxmp.service.impl;

import cn.hutool.json.JSONUtil;
import com.zhengqing.wxmp.enums.WxMpAutoReplyMsgTypeEnum;
import com.zhengqing.wxmp.enums.WxMpAutoReplyTypeEnum;
import com.zhengqing.wxmp.model.dto.WxMpMsgAutoReplyListDTO;
import com.zhengqing.wxmp.model.dto.WxMpReplyMsgDTO;
import com.zhengqing.wxmp.model.vo.WxMpMsgAutoReplyListVO;
import com.zhengqing.wxmp.service.IWxMpMsgAutoReplyService;
import com.zhengqing.wxmp.service.IWxMpMsgReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 微信公众号-消息回复 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxMpMsgReplyServiceImpl implements IWxMpMsgReplyService {

    private final IWxMpMsgAutoReplyService wxMpMsgAutoReplyService;

    private final WxMpService wxMpService;

    @Override
    public void replyMsg(WxMpReplyMsgDTO params) {
        log.info("[wx-mp] 收到用户消息：[{}]", JSONUtil.toJsonStr(params));
        String appId = params.getAppId();
        String fromUser = params.getFromUser();
        String content = params.getContent();
        WxMpAutoReplyTypeEnum wxMpAutoReplyTypeEnum = WxMpAutoReplyTypeEnum.getEnum(params.getType());

        // 1、查询规则
        List<WxMpMsgAutoReplyListVO> ruleList = this.wxMpMsgAutoReplyService.list(WxMpMsgAutoReplyListDTO.builder().appId(appId).build());
        ruleList = ruleList.stream()
                .filter(rule -> {
                    if (!StringUtils.hasText(content)) {
                        return false;
                    }
                    switch (wxMpAutoReplyTypeEnum) {
                        case 关注时回复:
                            return WxMpAutoReplyTypeEnum.关注时回复.getType().equals(rule.getType());
                        case 关键词回复:
                            String matchValue = rule.getMatchValue();
                            if (rule.getIsExactMatch()) {
                                return content.equals(matchValue);
                            } else {
                                return content.contains(matchValue);
                            }
                        default:
                            break;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        // 2、回复消息
        this.wxMpService.switchover(appId);
        ruleList.forEach(rule -> {
            String replyContent = rule.getReplyContent();
            try {
                switch (WxMpAutoReplyMsgTypeEnum.getEnum(rule.getReplyType())) {
                    case 文本:
                        this.wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.TEXT().toUser(fromUser).content(replyContent).build());
                        break;
                    case 图片:
                        this.wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.IMAGE().toUser(fromUser).mediaId(replyContent).build());
                        break;
                    case 语音:
                        break;
                    case 视频:
                        break;
                    case 音乐:
                        break;
                    case 图文:
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                log.error("[wx-mp] 消息回复异常：", e);
            }
        });
    }

}
