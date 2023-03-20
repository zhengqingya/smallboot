package com.zhengqing.wxmp.service;

import com.zhengqing.wxmp.model.dto.WxMpReplyMsgDTO;

/**
 * <p> 微信公众号-消息回复 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/3/20 18:36
 */
public interface IWxMpMsgReplyService {

    /**
     * 回复消息
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2023/3/20 18:19
     */
    void replyMsg(WxMpReplyMsgDTO params);

}
