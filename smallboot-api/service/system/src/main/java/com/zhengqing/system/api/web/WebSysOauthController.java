package com.zhengqing.system.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.system.model.dto.SysOauthRemoveBindDTO;
import com.zhengqing.system.model.dto.SysOauthSaveDTO;
import com.zhengqing.system.model.vo.SysOauthDataListVO;
import com.zhengqing.system.service.ISysOauthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 系统管理 - 用户三方授权 Controller
 * </p>
 *
 * @author zhengqingya
 * @description 可参考： https://justauth.wiki
 * @date 2020/6/21 21:18
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/oauth")
@Api(tags = "web-系统管理-三方登录接口")
public class WebSysOauthController extends BaseController {

    private final ISysOauthService sysOauthService;

    @RequestMapping("{oauthType}")
    @ApiOperation("第三方账号授权")
    public String handleOauth(@PathVariable String oauthType, HttpServletResponse response) {
        return this.sysOauthService.handleOauth(oauthType, response);
    }

    @RequestMapping("{oauthType}/callback")
    @ApiOperation("第三方账号授权回调处理")
    public void handleCallback(@PathVariable String oauthType, AuthCallback callback, HttpServletResponse response) {
        this.sysOauthService.handleCallback(oauthType, callback, response);
    }

    @PostMapping("bindThirdPart")
    @ApiOperation("第三方账号绑定")
    public Integer handleBindThirdPartData(@Validated @RequestBody SysOauthSaveDTO params) {
        return this.sysOauthService.addOrUpdateData(params);
    }

    @GetMapping("getOauthDataList")
    @ApiOperation("获取第三方账号绑定授权数据")
    public List<SysOauthDataListVO> getOauthDataList(@RequestParam Integer userId) {
        return this.sysOauthService.getOauthDataList(userId);
    }

    @PostMapping("removeBind")
    @ApiOperation("解除第三方账号绑定")
    public void removeBind(@RequestBody SysOauthRemoveBindDTO params) {
        this.sysOauthService.removeBind(params);
    }

}
