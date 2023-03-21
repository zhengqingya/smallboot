package com.zhengqing.wxmp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.wxmp.entity.WxMpTemplateMsg;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgSendDTO;
import com.zhengqing.wxmp.model.vo.WxMpTemplateMsgPageVO;

/**
 * <p>  微信公众号-模板消息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:29
 */
public interface IWxMpTemplateMsgService extends IService<WxMpTemplateMsg> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    IPage<WxMpTemplateMsgPageVO> page(WxMpTemplateMsgPageDTO params);

    /**
     * 同步公众号模板数据
     *
     * @param appId 公众号appId
     * @return void
     * @author zhengqingya
     * @date 2023/3/20 10:08
     */
    void sync(String appId);

    /**
     * 发送消息
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    void sendMsg(WxMpTemplateMsgSendDTO params);

}
