package com.zhengqing.wxmp.api;

import cn.hutool.json.JSONUtil;
import com.zhengqing.common.base.constant.BaseConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 微信公众号-授权 </p>
 *
 * @author zhengqingya
 * @description 记得返回值放行处理 {@link BaseConstant#RETURN_VALUE_HANDLER_EXCLUDE_API_LIST}
 * @date 2023/3/16 16:51
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wx/mp/oauth")
@Api(tags = {"微信公众号-授权"})
public class WxMpOauthController {
    private final WxMpService wxMpService;

    @Value("${smallboot.wx-mp-domain}")
    private String wxMpDomain;

    @GetMapping("/getAuthorizationUrl")
    @ApiOperation("获取授权URL")
    public String getAuthorizationUrl(@RequestParam String appId) {
        return this.wxMpService.switchoverTo(appId).getOAuth2Service().buildAuthorizationUrl(this.wxMpDomain + "/wx/mp/oauth/callback/" + appId, WxConsts.OAuth2Scope.SNSAPI_USERINFO, "STATE");
    }

    @SneakyThrows(Exception.class)
    @GetMapping("/callback/{appId}")
    @ApiOperation("回调-授权登录同意")
    public WxOAuth2UserInfo callback(@PathVariable String appId, @RequestParam String code) {
        WxOAuth2Service oAuth2Service = this.wxMpService.switchoverTo(appId).getOAuth2Service();
        WxOAuth2AccessToken wxOAuth2AccessToken = oAuth2Service.getAccessToken(code);
//        String accessToken = wxOAuth2AccessToken.getAccessToken();
//        String openId = wxOAuth2AccessToken.getOpenId();
        WxOAuth2UserInfo userInfo = oAuth2Service.getUserInfo(wxOAuth2AccessToken, "zh_CN");
        log.info("[微信公众号] 授权回调 用户信息：[{}]", JSONUtil.toJsonStr(userInfo));
        return userInfo;
    }

}
