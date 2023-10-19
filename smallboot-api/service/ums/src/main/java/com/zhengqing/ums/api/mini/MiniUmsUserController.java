package com.zhengqing.ums.api.mini;

import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.UmsUserContext;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.dto.UmsUserInfoDTO;
import com.zhengqing.ums.model.dto.UmsUserLoginDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户管理接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_UMS + "/user")
@Api(tags = "mini-用户")
public class MiniUmsUserController extends BaseController {

    private final IUmsUserService sysUserService;

    @ApiOpen
    @PostMapping("wxLogin")
    @ApiOperation("微信小程序登录")
    public UmsUserVO wxLogin(@RequestBody UmsUserLoginDTO params) {
        return this.sysUserService.login(params);
    }

    @ApiOpen
    @PostMapping("login")
    @ApiOperation("小程序登录")
    public UmsUserVO login(@RequestBody UmsUserLoginDTO params) {
        params.setDeptId(this.getMiniAppIdReDeptId());
        return this.sysUserService.login(params);
    }

    @GetMapping("bindPhone")
    @ApiOperation("绑定手机号 --- 需微信小程序后台进行相关认证")
    public UmsUserVO bindPhone(@RequestParam String code) {
        return this.sysUserService.getPhone(code);
    }

    @GetMapping("getUserInfo")
    @ApiOperation("获取用户信息")
    public UmsUserVO getUserInfo(@ModelAttribute UmsUserDTO params) {
        return this.sysUserService.getUserInfo(params);
    }

    @PutMapping("updateUserInfo")
    @ApiOperation("更新用户信息")
    public void updateUserInfo(@Validated @RequestBody UmsUserInfoDTO params) {
        params.setId(UmsUserContext.getUserId());
        this.sysUserService.updateUserInfo(params);
    }

}
