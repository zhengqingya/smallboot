package com.zhengqing.ums.api;


import cn.hutool.json.JSONUtil;
import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.sdk.douyin.service.model.vo.DyServiceMsgVO;
import com.zhengqing.common.sdk.douyin.service.util.DyServiceApiUtil;
import com.zhengqing.common.web.custom.noreturnhandle.NoReturnHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 抖音-事件回调通知 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/5 2:36 下午
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_DOUYIN)
@Api(tags = "抖音-事件回调通知")
public class DyCallbackController {

    /**
     * 域名/api/douyin/event/notice
     */
    @ApiOpen
    @NoReturnHandle
    @ApiOperation("事件通知")
    @PostMapping("/event/notice")
    public Object notice(@RequestBody(required = false) Object params) {
        // 【推送 component_ticket】 https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/authorization/componentticket
        // 开放平台服务器每隔 10 分钟推送过来
        log.info("[抖音] 事件通知：{}", JSONUtil.toJsonStr(params));
        TenantIdContext.setTenantId(10);
        Map<String, Object> noticeMap = JSONUtil.toBean(JSONUtil.toJsonStr(params), HashMap.class);
        String nonce = String.valueOf(noticeMap.get("Nonce"));
        String timeStamp = String.valueOf(noticeMap.get("TimeStamp"));
        String encrypt = String.valueOf(noticeMap.get("Encrypt"));
        String msgSignature = String.valueOf(noticeMap.get("MsgSignature"));

        // FIXME
        String tpToken = "xxx";
        String encodingAesKey = "xxx";

        // 验证消息签名
        DyServiceApiUtil.checkSign(msgSignature, tpToken, timeStamp, nonce, encrypt);

        // 解密消息
        DyServiceMsgVO dyServiceMsgVO = DyServiceApiUtil.decryptMsg(encodingAesKey, encrypt);
        if ("Ticket".equals(dyServiceMsgVO.getMsgType())) {
            String component_ticket = dyServiceMsgVO.getTicket();
            DyServiceApiUtil.saveComponentTicket(component_ticket);
        }
        return "success";
    }

}
