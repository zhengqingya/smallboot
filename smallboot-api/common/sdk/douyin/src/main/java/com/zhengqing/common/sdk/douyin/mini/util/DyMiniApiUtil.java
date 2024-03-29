package com.zhengqing.common.sdk.douyin.mini.util;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.sdk.douyin.config.DyProperty;
import com.zhengqing.common.sdk.douyin.mini.enums.DyMiniResultCodeEnum;
import com.zhengqing.common.sdk.douyin.mini.model.dto.DyMiniLoginDTO;
import com.zhengqing.common.sdk.douyin.mini.model.vo.DyMiniAccessTokenVO;
import com.zhengqing.common.sdk.douyin.mini.model.vo.DyMiniLoginVO;
import com.zhengqing.common.sdk.douyin.util.DyBaseApiUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p> 抖音接口请求工具类 </p>
 *
 * @author zhengqingya
 * @description 抖音小程序开发接口文档见 https://developer.open-douyin.com/docs/resource/zh-CN/mini-app/develop/server/server-api-introduction
 * @date 2022/7/28 15:40
 */
@Slf4j
@Component
public class DyMiniApiUtil {

    private static DyProperty dyProperty;

    @Autowired
    public DyMiniApiUtil(DyProperty dyProperty) {
        DyMiniApiUtil.dyProperty = dyProperty;
    }

    /**
     * 接口调用凭证
     */
    private final static String ACCESS_TOKEN = "douyin:mini:access_token:";


    /**
     * 获取 接口调用凭证
     * https://developer.open-douyin.com/docs/resource/zh-CN/mini-app/develop/server/interface-request-credential/get-access-token/
     *
     * @param appid  小程序 ID
     * @param secret 小程序的 APP Secret，可以在开发者后台获取
     * @return 接口调用凭证
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static String getAccessToken(String appid, String secret) {
        // 1、缓存中拿到accessToken
        String accessToken = RedisUtil.get(ACCESS_TOKEN);
        if (StrUtil.isNotBlank(accessToken)) {
            return accessToken;
        }

        // 2、若缓存中无值，可通过请求获取accessToken
        DyMiniAccessTokenVO dyMiniAccessTokenVO = DyBaseApiUtil.basePost(baseUrl() + "/api/apps/v2/token",
                new HashMap<String, String>(3) {{
                    this.put("appid", appid);
                    this.put("secret", secret);
                    // 获取 access_token 时值为 client_credential
                    this.put("grant_type", "client_credential");
                }},
                DyMiniAccessTokenVO.class);
        DyMiniAccessTokenVO.Data data = dyMiniAccessTokenVO.getData();
        Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(dyMiniAccessTokenVO.getErrNo()), dyMiniAccessTokenVO.getErr_tips());
        accessToken = data.getAccess_token();

        // 3、存入缓存
        RedisUtil.setEx(ACCESS_TOKEN, accessToken, data.getExpires_in() - 100, TimeUnit.SECONDS);
        return accessToken;
    }

    /**
     * 登录
     * https://developer.open-douyin.com/docs/resource/zh-CN/mini-app/develop/server/log-in/code-2-session/
     *
     * @param params 提交参数
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static DyMiniLoginVO.Data jscode2session(DyMiniLoginDTO params) {
        DyMiniLoginVO dyMiniLoginVO = DyBaseApiUtil.basePost(baseUrl() + "/api/apps/v2/jscode2session", params, DyMiniLoginVO.class);
        Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(dyMiniLoginVO.getErr_no()), dyMiniLoginVO.getErr_tips());
        return dyMiniLoginVO.getData();
    }

    /**
     * 敏感数据处理
     * https://partner.open-douyin.com/docs/resource/zh-CN/mini-app/develop/guide/open-capabilities/sensitive-data-process
     *
     * @param encryptedData 敏感数据
     * @param iv            对称解密算法初始向量
     * @param sessionKey    开发者通过code2session接口得到的session_key
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    @SneakyThrows
    public static String decrypt(String encryptedData, String iv, String sessionKey) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] sessionKeyBytes = decoder.decode(sessionKey);
        byte[] ivBytes = decoder.decode(iv);
        byte[] encryptedBytes = decoder.decode(encryptedData);

        // JDK does not support PKCS7Padding, use PKCS5Padding instead
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec skeySpec = new SecretKeySpec(sessionKeyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
        byte[] ret = cipher.doFinal(encryptedBytes);
        return new String(ret);
    }

    /**
     * 获取手机号
     * https://partner.open-douyin.com/docs/resource/zh-CN/mini-app/develop/guide/open-capabilities/acquire-phone-number-acquire/
     *
     * @param decryptJson 解密数据
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    @SneakyThrows
    public static String getPhone(String decryptJson) {
        Map<String, String> map = JSONUtil.toBean(decryptJson, HashMap.class);
        return map.get("phoneNumber");
    }

    // *********************************** ↓↓↓↓↓↓ 下面为公共业务调用方法 ↓↓↓↓↓↓ **************************************

    private static String baseUrl() {
        return dyProperty.getIsOnLine() ? dyProperty.getMiniApp().getApiPrefix() : dyProperty.getMiniApp().getApiPrefixSandbox();
    }

}
