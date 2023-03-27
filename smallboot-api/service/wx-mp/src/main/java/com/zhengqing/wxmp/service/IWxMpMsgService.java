package com.zhengqing.wxmp.service;

import com.zhengqing.wxmp.model.bo.WxMpTemplateMsgSendBO;
import com.zhengqing.wxmp.model.dto.WxMpReplyMsgDTO;

/**
 * <p> 微信公众号-消息处理 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/3/20 18:36
 */
public interface IWxMpMsgService {

    /**
     * 回复消息
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2023/3/20 18:19
     */
    void autoReplyMsg(WxMpReplyMsgDTO params);

    /**
     * 发送模板消息
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2023/3/20 18:19
     */
    void sendTemplateMsg(WxMpTemplateMsgSendBO params);

}
