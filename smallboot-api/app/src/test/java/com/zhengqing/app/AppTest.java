package com.zhengqing.app;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.util.AutoUpgradeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

@Slf4j
public class AppTest {

    @Test
    public void test() throws Exception {
        System.out.println("hello");
    }

    @Test
    public void test02() throws Exception {
        AES aes = SecureUtil.aes(AppConstant.AES_KEY);
        // 加密
        String encrypt = aes.encryptHex("123456");
        System.out.println(encrypt);
        // 解密
        String decryptStr = aes.decryptStr(encrypt);
        System.out.println(decryptStr);
    }

    @Test
    public void test03() throws Exception {
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String s = AutoUpgradeUtil.autoUpgrade(null);
        System.out.println(s);
    }

    @Test
    public void test04() throws Exception {
        System.out.println(UUID.randomUUID().toString());
    }

}
