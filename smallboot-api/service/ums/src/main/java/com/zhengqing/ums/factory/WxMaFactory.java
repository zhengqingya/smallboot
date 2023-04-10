package com.zhengqing.ums.factory;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.hutool.core.lang.Assert;
import com.zhengqing.ums.config.WxMaProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信小程序
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Service
@AllArgsConstructor
public class WxMaFactory {

    //    private WxMaService wxMaService;
    private WxMaProperty wxMaProperty;

    public WxMaService wxMaService() {
        String appid = this.wxMaProperty.getAppid();
        Assert.notBlank(appid, "请先配置微信小程序相关参数！");
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(appid);
        config.setSecret(this.wxMaProperty.getSecret());
        config.setMsgDataFormat(this.wxMaProperty.getMsgDataFormat());

        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }

}
