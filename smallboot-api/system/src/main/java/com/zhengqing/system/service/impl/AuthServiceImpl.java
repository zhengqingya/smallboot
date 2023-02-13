package com.zhengqing.common.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.zhengqing.common.auth.model.dto.AuthLoginDTO;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.auth.service.IAuthService;
import com.zhengqing.common.base.enums.AuthSourceEnum;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.service.ISysUserService;
import com.zhengqing.system.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 授权 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final ISysUserService sysUserService;

    @Override
    public AuthLoginVO login(AuthLoginDTO params) {
        String username = params.getUsername();
        String password = params.getPassword();

        SysUser sysUser = this.sysUserService.getUserByUsername(username);
        Assert.notNull(sysUser, "账号不存在！");
        boolean isValid = PasswordUtil.isValidPassword(password, sysUser.getPassword());

        // 校验原始密码是否正确
        Assert.isTrue(isValid, "密码错误！");

        // 登录
        StpUtil.login(JSONUtil.toJsonStr(
                JwtUserBO.builder()
                        .authSourceEnum(AuthSourceEnum.B)
                        .userId(String.valueOf(sysUser.getUserId()))
                        .userName(sysUser.getUsername())
                        .build()
        ));
        return AuthLoginVO.builder()
                .tokenName(StpUtil.getTokenName())
                .tokenValue(StpUtil.getTokenValue())
                .build();
    }

}
