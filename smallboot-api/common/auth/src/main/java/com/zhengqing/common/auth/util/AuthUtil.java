package com.zhengqing.common.auth.util;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p> 授权工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Component
public class AuthUtil {

    private static String tokenPrefix;

    @Autowired
    public AuthUtil(@Value("${sa-token.token-prefix:}") String tokenPrefix) {
        AuthUtil.tokenPrefix = tokenPrefix;
    }


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
        String tokenValue = StpUtil.getTokenValue();
        return AuthLoginVO.builder()
                .tokenName(StpUtil.getTokenName())
                .tokenValue(StrUtil.isNotBlank(tokenPrefix) ? tokenPrefix + " " + tokenValue : tokenValue)
                .build();
    }


}
