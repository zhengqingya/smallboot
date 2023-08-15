package com.zhengqing.common.auth.util;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> 授权工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
public class AuthUtil {

    /**
     * 登录认证
     *
     * @param jwtUserBO 用户token信息
     * @return 登录参数
     * @author zhengqingya
     * @date 2020/4/15 11:33
     */
    public static AuthLoginVO login(JwtUserBO jwtUserBO) {
        StpUtil.login(JSONUtil.toJsonStr(jwtUserBO));
        return AuthLoginVO.builder()
                .tokenName(StpUtil.getTokenName())
                .tokenValue(StpUtil.getTokenValue())
                .build();
    }


}
