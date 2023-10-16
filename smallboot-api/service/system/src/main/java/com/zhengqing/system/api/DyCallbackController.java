package com.zhengqing.system.api;


import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.sdk.douyin.service.model.vo.DyServiceMsgVO;
import com.zhengqing.common.sdk.douyin.service.util.DyServiceApiUtil;
import com.zhengqing.common.web.custom.noreturnhandle.NoReturnHandle;
import com.zhengqing.system.enums.SysConfigKeyEnum;
import com.zhengqing.system.model.vo.SysConfigVO;
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

    /**
     * 域名/api/douyin/event/notice/?
     */
    @ApiOpen
    @NoReturnHandle
    @ApiOperation("事件通知")
    @PostMapping("/event/notice/{tenantId}")
    public Object notice(@PathVariable Integer tenantId, @RequestBody(required = false) Object params) {
        // 【推送 component_ticket】 https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/authorization/componentticket
        // 开放平台服务器每隔 10 分钟推送过来
        log.info("[抖音] tenantId：{} 事件通知：{}", tenantId, JSONUtil.toJsonStr(params));
        TenantIdContext.setTenantId(tenantId);
        Map<String, Object> noticeMap = JSONUtil.toBean(JSONUtil.toJsonStr(params), HashMap.class);
        String nonce = String.valueOf(noticeMap.get("Nonce"));
        String timeStamp = String.valueOf(noticeMap.get("TimeStamp"));
        String encrypt = String.valueOf(noticeMap.get("Encrypt"));
        String msgSignature = String.valueOf(noticeMap.get("MsgSignature"));

        // 拿到消息验证相关数据
        Map<String, SysConfigVO> configMap = this.iSysConfigService.mapByKey(Lists.newArrayList(SysConfigKeyEnum.DOUYIN_TP_TOKEN.getKey(), SysConfigKeyEnum.DOUYIN_ENCODING_AES_KEY.getKey()));
        Assert.notEmpty(configMap, "tpToken和encodingAesKey未配置！");
        Object tpTokenVal = configMap.get(SysConfigKeyEnum.DOUYIN_TP_TOKEN.getKey()).getValue();
        Assert.notNull(tpTokenVal, "tpToken未配置！");
        String tpToken = String.valueOf(tpTokenVal);
        Object encodingAesKeyVal = configMap.get(SysConfigKeyEnum.DOUYIN_ENCODING_AES_KEY.getKey()).getValue();
        Assert.notNull(encodingAesKeyVal, "encodingAesKey未配置！");
        String encodingAesKey = String.valueOf(encodingAesKeyVal);

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

    /**
     * 域名/api/event/notice/2/$APPID$/
     */
    @ApiOpen
    @NoReturnHandle
    @ApiOperation("授权通知")
    @PostMapping("/event/notice/{tenantId}/{appid}")
    public Object authCallback(@PathVariable Integer tenantId, @PathVariable Integer appid, @RequestBody(required = false) Object params) {
        log.info("[抖音] tenantId:{} appid:{} 授权通知：{}", tenantId, appid, JSONUtil.toJsonStr(params));
        return "success";
    }

}
