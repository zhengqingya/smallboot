package com.zhengqing.common.base.util;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zhengqing.common.base.enums.AuthSourceEnum;
import com.zhengqing.common.base.model.bo.JwtUserBO;

import java.util.Date;

/**
 * <p> jwt工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/2/27 13:50
 */
public class JwtUtil {

    public static void main(String[] args) {
        System.out.println(sign(JwtUserBO.builder().userId("1").authSourceEnum(AuthSourceEnum.B).build(), 60000, "netty-server"));
    }

    /**
     * 生成jwt签名,指定时间后过期,一经生成不可修改，令牌在指定时间内一直有效
     *
     * @param userObj  用户信息
     * @param expireIn 过期时间，单位：秒
     * @param secret   秘钥
     * @return jwt token
     */
    public static String sign(JwtUserBO userObj, long expireIn, String secret) {
        Date date = new Date(System.currentTimeMillis() + expireIn * 1000);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                // 自定义存储的数据
                .withClaim("userObj", JSONUtil.toJsonStr(userObj))
                .withClaim("userId", userObj.getUserId())
                // 过期时间
                .withExpiresAt(date)
                // token加签加密
                .sign(algorithm);
    }

    /**
     * 校验token
     *
     * @param token  用户登录token
     * @param secret 秘钥
     * @return true：成功 false：失败
     */
    public static Boolean checkSign(String token, String secret) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据token获取userId
     *
     * @param token 登录token
     * @return 用户id
     */
    public static Long getUserId(String token) {
        return JWT.decode(token).getClaim("userId").asLong();
    }

    /**
     * 根据token获取用户数据
     *
     * @param token 用户登录token
     * @return 用户数据
     */
    public static JwtUserBO get(String token) {
        return JSONUtil.toBean(JWT.decode(token).getClaim("userObj").asString(), JwtUserBO.class);
    }

}
