package com.zhengqing.common.sdk.douyin.service.util;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.sdk.douyin.config.DyProperty;
import com.zhengqing.common.sdk.douyin.mini.enums.DyMiniResultCodeEnum;
import com.zhengqing.common.sdk.douyin.mini.model.vo.DyMiniAccessTokenVO;
import com.zhengqing.common.sdk.douyin.service.model.dto.DyServiceLoginDTO;
import com.zhengqing.common.sdk.douyin.service.model.vo.DyServiceLoginVO;
import com.zhengqing.common.sdk.douyin.util.DyBaseApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p> 抖音接口请求工具类 </p>
 *
 * @author zhengqingya
 * @description 抖音服务商开发接口文档见 https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/format
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
     * 接口调用凭证
     */
    private final static String ACCESS_TOKEN = "douyin:service:access_token:";


    /**
     * 获取 接口调用凭证
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
     * https://partner.open-douyin.com/docs/resource/zh-CN/thirdparty/API/smallprogram/auth-app-manage/login/code2session
     *
     * @param params 提交参数
     * @return 结果
     * @author zhengqingya
     * @date 2022/7/28 15:40
     */
    public static DyServiceLoginVO.Data code2session(DyServiceLoginDTO params) {
        DyServiceLoginVO dyServiceLoginVO = DyBaseApiUtil.basePost("https://open.microapp.bytedance.com/openapi/v1/microapp/code2session", params, DyServiceLoginVO.class);
        Assert.isTrue(DyMiniResultCodeEnum.SUCCESS.getCode().equals(dyServiceLoginVO.getErrno()), dyServiceLoginVO.getMessage());
        return dyServiceLoginVO.getData();
    }

    // *********************************** ↓↓↓↓↓↓ 下面为公共业务调用方法 ↓↓↓↓↓↓ **************************************

    private static String baseUrl() {
        return dyProperty.getIsOnLine() ? dyProperty.getMiniApp().getApiPrefix() : dyProperty.getMiniApp().getApiPrefixSandbox();
    }

}
