package com.zhengqing.wxmp.api;

import com.zhengqing.common.base.constant.ServiceConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 微信公众号-授权 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/3/16 16:51
 */
@AllArgsConstructor
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WXMP + "/oauth")
@Api(tags = {"微信公众号-授权"})
public class WxMpOauthController {
    private final WxMpService wxMpService;

    @GetMapping("/getAuthorizationUrl")
    @ApiOperation("获取授权URL")
    public String getAuthorizationUrl(@RequestParam String appId) {
        return this.wxMpService.switchoverTo(appId).getOAuth2Service().buildAuthorizationUrl("127.0.0.1:8080", WxConsts.OAuth2Scope.SNSAPI_USERINFO, "STATE");
    }

}
