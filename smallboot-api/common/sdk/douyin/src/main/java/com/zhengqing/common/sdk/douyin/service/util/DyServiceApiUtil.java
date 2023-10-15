package com.zhengqing.common.sdk.douyin.service.util;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.sdk.douyin.config.DyProperty;
import com.zhengqing.common.sdk.douyin.mini.enums.DyMiniResultCodeEnum;
import com.zhengqing.common.sdk.douyin.service.model.dto.DyServiceLoginDTO;
import com.zhengqing.common.sdk.douyin.service.model.vo.*;
import com.zhengqing.common.sdk.douyin.util.DyBaseApiUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p> 抖音接口请求工具类 </p>
 *
 * @author zhengqingya
 * @description 抖音服务商开发接口文档见 https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/format
 * 授权环节说明： https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/overview-guide/smallprogram/authorization
 * @date 2022/7/28 15:40
 */
@Slf4j
@Component
public class DyServiceApiUtil {

    private static DyProperty dyProperty;

    @Autowired
    public DyServiceApiUtil(DyProperty dyProperty) {
        DyServiceApiUtil.dyProperty = dyProperty;
    }

    /**
     * 推送 component_ticket
     */
    private final static String COMPONENT_TICKET = "douyin:service:component_ticket:";
    /**
     * 获取第三方小程序接口调用凭据
     */
    private final static String COMPONENT_ACCESS_TOKEN = "douyin:service:component_access_token:";
    /**
     * 获取预授权码
     */
    private final static String PRE_AUTH_CODE = "douyin:service:pre_auth_code:";
    /**
     * 获取授权链接
     */
    private final static String GEN_LINK = "douyin:service:gen_link:";
    /**
     * 商家授权码
     */
    private final static String AUTHORIZATION_CODE = "douyin:service:authorization_code:";
    /**
     * 获取授权小程序接口调用凭据
     */
    private final static String AUTHORIZER_ACCESS_TOKEN = "douyin:service:authorizer_access_token:";


    /**
     * 登录
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/auth-app-manage/login/code2session
     *
     * @param params 提交参数
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static DyServiceLoginVO.Data code2session(DyServiceLoginDTO params) {
        DyServiceLoginVO dyServiceLoginVO = DyBaseApiUtil.baseGet("https://open.microapp.bytedance.com/openapi/v1/microapp/code2session", params, DyServiceLoginVO.class);
        Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(dyServiceLoginVO.getErrno()), dyServiceLoginVO.getMessage());
        return dyServiceLoginVO.getData();
    }

    /**
     * 提交代码
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/auth-app-manage/develop/upload-code
     *
     * @param component_appid         第三方小程序应用 appid
     * @param authorizer_access_token 授权小程序接口调用凭据
     * @param template_id             模板 id，取自第三方小程序应用【开发】-【模板库】
     * @param user_desc               提交描述
     * @param user_version            提交版本
     * @param ext_json                ext.json 配置的字符串形式
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static void uploadCode(String component_appid, String authorizer_access_token, Integer template_id, String user_desc, String user_version, String ext_json) {
        HashMap<String, Object> map = DyBaseApiUtil.basePostParamsBody("https://open.microapp.bytedance.com/openapi/v1/microapp/package/upload",
                new HashMap<String, String>(2) {{
                    this.put("component_appid", component_appid);
                    this.put("authorizer_access_token", authorizer_access_token);
                }},
                new HashMap<String, Object>(3) {{
                    this.put("template_id", template_id);
                    this.put("user_desc", user_desc);
                    this.put("user_version", user_version);
                    this.put("ext_json", ext_json);
                }}, HashMap.class);
        Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(Long.valueOf(String.valueOf(map.get("errno")))), String.valueOf(map.get("message")));
    }

    /**
     * 提审代码
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/auth-app-manage/develop/audit-code
     *
     * @param component_appid         第三方小程序应用 appid
     * @param authorizer_access_token 授权小程序接口调用凭据
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static void audit(String component_appid, String authorizer_access_token) {
        HashMap<String, String> map = DyBaseApiUtil.basePostParamsBody("https://open.microapp.bytedance.com/openapi/v2/microapp/package/audit",
                new HashMap<String, String>(2) {{
                    this.put("component_appid", component_appid);
                    this.put("authorizer_access_token", authorizer_access_token);
                }},
                new HashMap<String, Object>(3) {{
                    this.put("hostNames", Lists.newArrayList("douyin"));
//                    this.put("auditNote", null);
//                    this.put("auditWay", null);
                }}, HashMap.class);
        Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(Long.valueOf(String.valueOf(map.get("errno")))), map.get("message"));
    }

    /**
     * 发布代码
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/auth-app-manage/develop/release
     *
     * @param component_appid         第三方小程序应用 appid
     * @param authorizer_access_token 授权小程序接口调用凭据
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static void release(String component_appid, String authorizer_access_token) {
        HashMap<String, String> map = DyBaseApiUtil.basePostParams("https://open.microapp.bytedance.com/openapi/v1/microapp/package/release",
                new HashMap<String, String>(2) {{
                    this.put("component_appid", component_appid);
                    this.put("authorizer_access_token", authorizer_access_token);
                }}, HashMap.class);
        Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(Long.valueOf(String.valueOf(map.get("errno")))), map.get("message"));
    }

    // *********************************** ↓↓↓↓↓↓ 下面为公共业务方法 ↓↓↓↓↓↓ **************************************

    private static String baseUrl() {
        return dyProperty.getIsOnLine() ? dyProperty.getMiniApp().getApiPrefix() : dyProperty.getMiniApp().getApiPrefixSandbox();
    }

    public static void saveComponentTicket(String component_ticket) {
        // https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/authorization/componentticket
        // 推送 component_ticket  有效期为 3 小时。
        RedisUtil.setEx(COMPONENT_TICKET + TenantIdContext.getTenantId(), component_ticket, 60 * 2 + 50, TimeUnit.MINUTES);
    }

    /**
     * 获取第三方小程序接口调用凭据
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/authorization/componentaccesstoken
     *
     * @param component_appid     第三方小程序应用 appid
     * @param component_appsecret 第三方小程序应用 appsecret
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static String component_access_token(String component_appid, String component_appsecret) {
        String key = COMPONENT_ACCESS_TOKEN + component_appid;
        // 1、缓存中获取
        String component_access_token = RedisUtil.get(key);
        if (StrUtil.isNotBlank(component_access_token)) {
            return component_access_token;
        }

        // 2、若缓存中无值，可通过请求获取
        String component_ticket = RedisUtil.get(COMPONENT_TICKET + TenantIdContext.getTenantId());
        Assert.notBlank(component_ticket, "component_ticket数据丢失！");
        DyServiceComponentAccessTokenVO dyServiceComponentAccessTokenVO = DyBaseApiUtil.baseGet("https://open.microapp.bytedance.com/openapi/v1/auth/tp/token",
                new HashMap<String, String>(3) {{
                    this.put("component_appid", component_appid);
                    this.put("component_appsecret", component_appsecret);
                    this.put("component_ticket", component_ticket);
                }},
                DyServiceComponentAccessTokenVO.class);
        Long errno = dyServiceComponentAccessTokenVO.getErrno();
        if (errno != null) {
            Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(errno), dyServiceComponentAccessTokenVO.getMessage());
        }
        component_access_token = dyServiceComponentAccessTokenVO.getComponent_access_token();

        // 3、存入缓存
        RedisUtil.setEx(key, component_access_token, dyServiceComponentAccessTokenVO.getExpires_in() - 10, TimeUnit.SECONDS);
        return component_access_token;
    }

    /**
     * 获取预授权码
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/authorization/preauthcode
     *
     * @param component_appid        第三方小程序应用 appid
     * @param component_access_token 第三方小程序应用接口调用凭据
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static String pre_auth_code(String component_appid, String component_access_token) {
        String key = PRE_AUTH_CODE + component_appid;
        // 1、缓存中获取
        String pre_auth_code = RedisUtil.get(key);
        if (StrUtil.isNotBlank(pre_auth_code)) {
            return pre_auth_code;
        }

        // 2、若缓存中无值，可通过请求获取
        DyServicePreAuthCodeVO dyServicePreAuthCodeVO = DyBaseApiUtil.basePost("https://open.microapp.bytedance.com/openapi/v2/auth/pre_auth_code",
                new HashMap<String, String>(3) {{
                    this.put("component_appid", component_appid);
                    this.put("component_access_token", component_access_token);
                }},
//                new HashMap<String, String>(3) {{
//                    this.put("pre_auth_code_type", "2");
//                    this.put("app_name", null);
//                    this.put("app_icon", null);
//                }},
                DyServicePreAuthCodeVO.class);
        pre_auth_code = dyServicePreAuthCodeVO.getPre_auth_code();

        // 3、存入缓存
        RedisUtil.setEx(key, pre_auth_code, dyServicePreAuthCodeVO.getExpires_in() - 10, TimeUnit.SECONDS);
        return pre_auth_code;
    }

    /**
     * 直接获取授权链接
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/authorization/genlink
     *
     * @param component_appid        第三方小程序应用 appid
     * @param component_access_token 第三方小程序应用接口调用凭据
     * @param redirect_uri           授权回调地址 授权成功后会跳转到该地址并给出授权码
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static String gen_link(String component_appid, String component_access_token, String redirect_uri) {
        String key = GEN_LINK + component_appid;
        // 1、缓存中获取
        String gen_link = RedisUtil.get(key);
        if (StrUtil.isNotBlank(gen_link)) {
            return gen_link;
        }

        // 2、若缓存中无值，可通过请求获取
        HashMap<String, String> map = DyBaseApiUtil.basePostParamsBody("https://open.microapp.bytedance.com/openapi/v2/auth/gen_link",
                new HashMap<String, String>(3) {{
                    this.put("component_appid", component_appid);
                    this.put("component_access_token", component_access_token);
                }},
                new HashMap<String, String>(4) {{
//                    this.put("link_type", "2");
//                    this.put("app_name", null);
//                    this.put("app_icon", null);
                    this.put("redirect_uri", redirect_uri);
                }},
                HashMap.class);
        String errno = map.get("errno");
        if (StrUtil.isNotBlank(errno)) {
            Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(Long.valueOf(String.valueOf(errno))), String.valueOf(map.get("message")));
        }
        String link = map.get("link");

        // 3、存入缓存
        RedisUtil.setEx(key, gen_link, 23, TimeUnit.HOURS);
        return link;
    }


    /**
     * 找回授权码
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/authorization/findauthorizationcode
     *
     * @param component_appid        第三方小程序应用 appid
     * @param component_access_token 第三方小程序应用接口调用凭据
     * @param authorization_appid    授权小程序 appid
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static String retrieve_authorization_code(String component_appid, String component_access_token, String authorization_appid) {
        String key = AUTHORIZATION_CODE + component_appid + ":" + authorization_appid;
        // 1、缓存中获取
        String authorization_code = RedisUtil.get(key);
        if (StrUtil.isNotBlank(authorization_code)) {
            return authorization_code;
        }

        // 2、若缓存中无值，可通过请求获取
        HashMap<String, Object> map = DyBaseApiUtil.basePostParams("https://open.microapp.bytedance.com/openapi/v1/auth/retrieve",
                new HashMap<String, String>(3) {{
                    this.put("component_appid", component_appid);
                    this.put("component_access_token", component_access_token);
                    this.put("authorization_appid", authorization_appid);
                }},
                HashMap.class);
        Object errno = map.get("errno");
        if (errno != null) {
            Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(errno), (String) map.get("message"));
        }
        authorization_code = String.valueOf(map.get("authorization_code"));
        RedisUtil.setEx(key, authorization_code, Long.valueOf(map.get("expires_in").toString()) - 10, TimeUnit.SECONDS);
        return authorization_code;
    }

    /**
     * 获取授权小程序接口调用凭据
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/authorization/authorizeraccesstoken
     *
     * @param component_appid        第三方小程序应用 appid
     * @param component_access_token 第三方小程序应用接口调用凭据
     * @param authorization_code     授权码
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static String authorizer_access_token(String component_appid, String component_access_token, String authorization_code) {
        String key = AUTHORIZER_ACCESS_TOKEN + component_appid;
        // 1、缓存中获取
        String authorizer_access_token = RedisUtil.get(key);
        if (StrUtil.isNotBlank(authorizer_access_token)) {
            return authorizer_access_token;
        }

        // 2、若缓存中无值，可通过请求获取
        DyServiceAuthorizerAccessTokenVO dyServiceAuthorizerAccessTokenVO = DyBaseApiUtil.baseGet("https://open.microapp.bytedance.com/openapi/v1/oauth/token",
                new HashMap<String, String>(3) {{
                    this.put("component_appid", component_appid);
                    this.put("component_access_token", component_access_token);
                    this.put("authorization_code", authorization_code);
                    this.put("grant_type", "app_to_tp_authorization_code");
                }},
                DyServiceAuthorizerAccessTokenVO.class);
        authorizer_access_token = dyServiceAuthorizerAccessTokenVO.getAuthorizer_access_token();

        // 3、存入缓存
        RedisUtil.setEx(key, authorizer_access_token, dyServiceAuthorizerAccessTokenVO.getExpires_in() - 10, TimeUnit.SECONDS);
        return authorizer_access_token;
    }


    // *********************************** ↓↓↓↓↓↓ 下面为抖音回调所需验证方法 ↓↓↓↓↓↓ **************************************
    // https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/overview-guide/smallprogram/encryption

    @SneakyThrows
    public static void checkSign(String msgSignature, String tpToken, String timestamp, String nonce, String encrypt) {
        String newMsgSignature = getMsgSignature(tpToken, timestamp, nonce, encrypt);
        boolean res = msgSignature.equals(newMsgSignature);
        Assert.isTrue(res, "抖音验证消息签名失败！");
    }

    private static String getMsgSignature(String tpToken, String timestamp, String nonce, String encrypt) throws Exception {
        String[] values = new String[]{tpToken, timestamp, nonce, encrypt};
        Arrays.sort(values);

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value);
        }

        String str = sb.toString();

        try {
            //指定sha1算法
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());

            //获取字节数组
            byte[] messageDigestByte = messageDigest.digest();

            StringBuilder hexStr = new StringBuilder();
            // 字节数组转换为十六进制数
            for (byte b : messageDigestByte) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }

            return hexStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("不能生成签名");
        }
    }

    @SneakyThrows
    public static DyServiceMsgVO decryptMsg(String encodingAesKey, String encrypt) {
        MsgDecrypt msgDecrypt = new MsgDecrypt(encodingAesKey);
        return JSONUtil.toBean(msgDecrypt.decrypt(encrypt), DyServiceMsgVO.class);
    }

}
