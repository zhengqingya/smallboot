package com.zhengqing.common.auth.service.impl;

import cn.hutool.core.lang.Assert;
import com.zhengqing.common.auth.model.dto.AuthLoginDTO;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.auth.service.IAuthService;
import com.zhengqing.common.auth.util.AuthUtil;
import com.zhengqing.common.base.enums.AuthSourceEnum;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import com.zhengqing.system.model.dto.SysUserPermDTO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.ISysPermBusinessService;
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

    private final ISysPermBusinessService sysPermBusinessService;

    @Override
    public AuthLoginVO login(AuthLoginDTO params) {
        String username = params.getUsername();
        String password = params.getPassword();

        SysUserPermVO userPerm = this.sysPermBusinessService.getUserPerm(SysUserPermDTO.builder().username(username).build());
        boolean isValid = PasswordUtil.isValidPassword(password, userPerm.getPassword());

        // 校验原始密码是否正确
        Assert.isTrue(isValid, "密码错误！");

        // 登录
        return AuthUtil.login(
                JwtUserBO.builder()
                        .authSourceEnum(AuthSourceEnum.B)
                        .userId(String.valueOf(userPerm.getUserId()))
                        .username(userPerm.getUsername())
                        .roleCodeList(userPerm.getRoleCodeList())
                        .build()
        );
    }

}
