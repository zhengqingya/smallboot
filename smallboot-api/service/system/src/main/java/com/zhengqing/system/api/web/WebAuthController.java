package com.zhengqing.system.api.web;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.auth.model.dto.AuthLoginDTO;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.auth.service.IAuthService;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.web.custom.noreturnhandle.NoReturnHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 授权认证 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/5 2:36 下午
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB + "/auth")
@Api(tags = "授权认证api")
@RequiredArgsConstructor
public class WebAuthController {

    private final IAuthService iAuthService;
    @Value("${sa-token.token-prefix:}")
    private String tokenPrefix;

    @ApiOpen
    @PostMapping("login")
    @ApiOperation("登录")
    public ApiResult<AuthLoginVO> login(@Validated @RequestBody AuthLoginDTO params) {
        Assert.notNull(TenantIdContext.getTenantId(), "请先选择租户！");
        return ApiResult.ok(this.iAuthService.login(params));
    }

    @ApiOpen
    @DeleteMapping("logout")
    @ApiOperation("注销登录")
    public void logout() {
        StpUtil.logout();
    }

    @ApiOpen
    @NoReturnHandle
    @ApiOperation("登录(knife4j授权使用)")
    @PostMapping("knife4j")
    public Map<String, String> knife4j(@ApiIgnore Principal principal, @ModelAttribute AuthLoginDTO params) {
        TenantIdContext.setTenantId(AppConstant.SMALL_BOOT_TENANT_ID);
        AuthLoginVO oAuth2AccessToken = this.iAuthService.login(params);
        /**
         * UI授权成功后取值逻辑见：{@see /knife4j-spring-ui/3.0.3/knife4j-spring-ui-3.0.3.jar!/META-INF/resources/webjars/oauth/oauth2.html}
         *                 that.cacheValue.accessToken=data.token_type+" "+data.access_token;
         *                 that.cacheValue.tokenType=data.token_type;
         */
        return new HashMap<String, String>(2) {{
            this.put("token_type", WebAuthController.this.tokenPrefix);
            this.put("access_token", oAuth2AccessToken.getTokenValue().split(" ")[1]);
        }};
    }

}
