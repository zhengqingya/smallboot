package com.zhengqing.app;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.util.AutoUpgradeUtil;
import com.zhengqing.common.core.util.MyCollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class AppTest {

    @Test
    public void test5() throws Exception {
        Map<String, Integer> map = MyCollectionUtil.toMap(this.list, User::getName, User::getAge, (oldData, newData) -> newData);
        System.out.println(map);
    }

    @Test
    public void test4() throws Exception {
        System.out.println("hello");
    }

    @Test
    public void test03() throws Exception {
        AES aes = SecureUtil.aes(AppConstant.AES_KEY);
        // 加密
        String encrypt = aes.encryptHex("123456");
        System.out.println(encrypt);
        // 解密
        String decryptStr = aes.decryptStr(encrypt);
        System.out.println(decryptStr);
    }

    @Test
    public void test02() throws Exception {
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String s = AutoUpgradeUtil.autoUpgrade(null);
        System.out.println(s);
    }

    @Test
    public void test01() throws Exception {
        System.out.println(UUID.randomUUID().toString());
    }

    List<User> list = Lists.newArrayList(
            User.builder().name("小白").age(10).build(),
            User.builder().name("java").age(18).build(),
            User.builder().name("python").age(20).build()
    );

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    static class User {
        private Boolean isDeleted;
        private String name;
        private Integer age;
    }

}
