package com.zhengqing.common.auth.util;

import cn.dev33.satoken.error.SaErrorCode;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import com.zhengqing.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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

    private final static String JWT_USER_KEY = "smallboot:login:";

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
        String userId = jwtUserBO.getUserId();
        Assert.notBlank(userId, "用户id不能为空！");

        // 登录
        String userKey = jwtUserBO.getAuthSourceEnum().getValue() + ":" + userId;
        StpUtil.login(userKey);

        // 将登录信息存储到redis
        RedisUtil.setEx(JWT_USER_KEY + userKey, JSONUtil.toJsonStr(jwtUserBO), StpUtil.getTokenTimeout(), TimeUnit.SECONDS);

        String tokenValue = StpUtil.getTokenValue();
        return AuthLoginVO.builder()
                .tokenName(StpUtil.getTokenName())
                .tokenValue(StrUtil.isNotBlank(tokenPrefix) ? tokenPrefix + " " + tokenValue : tokenValue)
                .build();
    }

    /**
     * 根据用户id注销
     *
     * @param userId 用户ID
     * @return void
     * @author zhengqingya
     * @date 2020/4/15 11:33
     */
    public static void logout(Object userId) {
        StpUtil.logout(userId);
    }

    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     * @author zhengqingya
     * @date 2020/4/15 11:33
     */
    public static JwtUserBO getLoginUser() {
        String userObj = RedisUtil.get(JWT_USER_KEY + StpUtil.getLoginId().toString());
        if (StrUtil.isBlank(userObj)) {
            throw NotLoginException.newInstance(StpUtil.getLoginType(), NotLoginException.NOT_TOKEN).setCode(SaErrorCode.CODE_11011);
        }
        return JSONUtil.toBean(userObj, JwtUserBO.class);
    }

}
