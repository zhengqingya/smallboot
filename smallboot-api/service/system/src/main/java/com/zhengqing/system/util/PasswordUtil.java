package com.zhengqing.system.util;

import cn.hutool.crypto.SecureUtil;
import com.zhengqing.common.base.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * <p> 加密工具 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/1/30 14:29
 */
@Slf4j
public class PasswordUtil {


    /**
     * 校验密码
     *
     * @param password       未加密密码
     * @param encodePassword 加密后的密码 -- 指数据库中存储的密码
     * @return true:正确 false:错误
     * @author zhengqingya
     * @date 2023/1/30 13:42
     */
    public static boolean isValidPassword(String password, String encodePassword) {
        String decryptStr = SecureUtil.aes(AppConstant.AES_KEY).decryptStr(encodePassword);
        return Objects.equals(password, decryptStr);
    }

    /**
     * 求加密后的密码
     *
     * @param password 密码
     * @return 加密后的密码
     * @author zhengqingya
     * @date 2023/1/30 14:23
     */
    public static String encodePassword(String password) {
        return SecureUtil.aes(AppConstant.AES_KEY).encryptHex(password);
    }

    /**
     * 解密
     *
     * @param encodePassword 加密后的密码
     * @return 加密后的密码
     * @author zhengqingya
     * @date 2023/1/30 14:23
     */
    public static String decrypt(String encodePassword) {
        return SecureUtil.aes(AppConstant.AES_KEY).decryptStr(encodePassword);
    }

}
