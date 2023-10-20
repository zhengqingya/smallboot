package com.zhengqing.system.api;


import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.sdk.douyin.service.model.vo.DyServiceMsgVO;
import com.zhengqing.common.sdk.douyin.service.util.DyServiceApiUtil;
import com.zhengqing.common.web.custom.noreturnhandle.NoReturnHandle;
import com.zhengqing.system.model.vo.SysAppServiceConfigDetailVO;
import com.zhengqing.system.service.ISysAppServiceConfigService;
import com.zhengqing.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_DOUYIN)
@Api(tags = "抖音-事件回调通知")
public class DyCallbackController {

    private final ISysConfigService iSysConfigService;
    private final ISysAppServiceConfigService iSysAppServiceConfigService;

    /**
     * 域名/api/douyin/event/notice/2
     */
    @ApiOpen
    @NoReturnHandle
    @ApiOperation("事件通知")
    @PostMapping("/event/notice/2")
    public Object notice(@RequestBody(required = false) Object params) {
        // 【推送 component_ticket】 https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/authorization/componentticket
        // 开放平台服务器每隔 10 分钟推送过来
        log.info("[抖音]  事件通知：{}", JSONUtil.toJsonStr(params));
        TenantIdContext.removeFlag();
        Map<String, Object> noticeMap = JSONUtil.toBean(JSONUtil.toJsonStr(params), HashMap.class);
        String nonce = String.valueOf(noticeMap.get("Nonce"));
        String timeStamp = String.valueOf(noticeMap.get("TimeStamp"));
        String encrypt = String.valueOf(noticeMap.get("Encrypt"));
        String msgSignature = String.valueOf(noticeMap.get("MsgSignature"));

        // 拿到消息验证相关数据
        SysAppServiceConfigDetailVO sysAppServiceConfigDetailVO = this.iSysAppServiceConfigService.detail();
        String tpToken = sysAppServiceConfigDetailVO.getTpToken();
        String encodingAesKey = sysAppServiceConfigDetailVO.getEncodingAesKey();
        Assert.notEmpty(tpToken, "tpToken未配置！");
        Assert.notEmpty(encodingAesKey, "encodingAesKey未配置！");

        // 验证消息签名
        DyServiceApiUtil.checkSign(msgSignature, tpToken, timeStamp, nonce, encrypt);

        // 解密消息
        DyServiceMsgVO dyServiceMsgVO = DyServiceApiUtil.decryptMsg(encodingAesKey, encrypt);
        if ("Ticket".equals(dyServiceMsgVO.getMsgType())) {
            String component_ticket = dyServiceMsgVO.getTicket();
            DyServiceApiUtil.saveComponentTicket(sysAppServiceConfigDetailVO.getId(), component_ticket);
        }
        return "success";
    }

    /**
     * 域名/api/douyin/event/notice/2/$APPID$/
     */
    @ApiOpen
    @NoReturnHandle
    @ApiOperation("授权通知")
    @PostMapping("/event/notice/2/{appid}/")
    public Object authCallback(@PathVariable Integer appid, @RequestBody(required = false) Object params) {
        log.info("[抖音] appid:{} 授权通知：{}", appid, JSONUtil.toJsonStr(params));
        TenantIdContext.removeFlag();
        return "success";
    }

}
