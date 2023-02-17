package com.zhengqing.system.api;


import cn.dev33.satoken.stp.StpUtil;
import com.zhengqing.common.auth.model.dto.AuthLoginDTO;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.auth.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 授权认证 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/5 2:36 下午
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "授权认证api")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("login")
    @ApiOperation("登录")
    public AuthLoginVO login(@Validated @RequestBody AuthLoginDTO params) {
        return this.authService.login(params);
    }

    @DeleteMapping("logout")
    @ApiOperation("注销登录")
    public void logout() {
        StpUtil.logout();
    }

}
