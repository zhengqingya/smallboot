package com.zhengqing.pay.factory;

import cn.hutool.core.lang.Assert;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.zhengqing.pay.config.WxPayProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信支付
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Service
@AllArgsConstructor
public class WxPayFactory {

    private final WxPayProperty wxPayProperty;

    public WxPayService wxPayService() {
        String appId = this.wxPayProperty.getAppId();
        Assert.notBlank(appId, "请先配置微信支付相关参数！");

        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(appId));
        payConfig.setMchId(StringUtils.trimToNull(this.wxPayProperty.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(this.wxPayProperty.getMchKey()));
        payConfig.setSubAppId(StringUtils.trimToNull(this.wxPayProperty.getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(this.wxPayProperty.getSubMchId()));
        payConfig.setKeyPath(StringUtils.trimToNull(this.wxPayProperty.getKeyPath()));

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);

        // 如果是多商户模式可通过addConfig添加多个配置，然后支付的时候先调用switchoverTo切换到相应环境下
//        wxPayService.addConfig(this.wxPayProperty.getMchId(), payConfig);
//        wxPayService.switchoverTo(this.wxPayProperty.getMchId());

        return wxPayService;
    }

}
