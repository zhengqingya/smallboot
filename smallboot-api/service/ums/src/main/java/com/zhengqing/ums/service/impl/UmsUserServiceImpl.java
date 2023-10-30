package com.zhengqing.ums.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.auth.util.AuthUtil;
import com.zhengqing.common.base.enums.AuthSourceEnum;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.db.util.TenantUtil;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.sdk.douyin.mini.util.DyMiniApiUtil;
import com.zhengqing.common.sdk.douyin.service.model.dto.DyServiceLoginDTO;
import com.zhengqing.common.sdk.douyin.service.model.vo.DyServiceLoginVO;
import com.zhengqing.common.sdk.douyin.service.util.DyServiceApiUtil;
import com.zhengqing.system.model.vo.SysAppServiceConfigDetailVO;
import com.zhengqing.system.service.ISysAppConfigService;
import com.zhengqing.system.service.ISysAppServiceConfigService;
import com.zhengqing.system.service.ISysUserService;
import com.zhengqing.ums.constant.UmsConstant;
import com.zhengqing.ums.entity.UmsUser;
import com.zhengqing.ums.enums.MiniTypeEnum;
import com.zhengqing.ums.factory.WxMaFactory;
import com.zhengqing.ums.mapper.UmsUserMapper;
import com.zhengqing.ums.model.dto.*;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.model.vo.WebUmsUserPageVO;
import com.zhengqing.ums.service.IUmsUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService {

    private final UmsUserMapper umsUserMapper;
    private final WxMaFactory wxMaFactory;
    private final ISysAppConfigService iSysAppConfigService;
    private final ISysAppServiceConfigService iSysAppServiceConfigService;
    private final ISysUserService iSysUserService;

    @Override
    public UmsUser detail(Long id) {
        UmsUser umsUser = this.umsUserMapper.selectById(id);
        Assert.notNull(umsUser, "用户不存在！");
        return umsUser;
    }

    @Override
    public UmsUserVO getUser(Long id) {
        return this.getUserInfo(UmsUserDTO.builder().userId(id).build());
    }

    @Override
    public UmsUserVO getUserInfo(UmsUserDTO params) {
        UmsUserVO umsUserVO = this.umsUserMapper.selectUserInfo(params);
        Assert.notNull(umsUserVO, "用户不存在！");
        return umsUserVO;
    }

    @Override
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public UmsUserVO login(UmsUserLoginDTO params) {
        if (params.getIsLocalLogin()) {
            return this.getLocalLogin();
        }
        String code = params.getCode();
        Integer type = params.getType();
        String appid = params.getAppId();
        String openid = null;
        String unionid = null;
        String sessionKey = null;
        switch (MiniTypeEnum.getEnum(type)) {
            case 微信小程序:
                WxMaJscode2SessionResult wxMaJscode2SessionResult = this.wxMaFactory.wxMaService().jsCode2SessionInfo(code);
                openid = wxMaJscode2SessionResult.getOpenid();
                break;
            case 抖音小程序:
                // 模板代开发
//                SysAppConfigBO sysAppConfigBO = this.iSysAppConfigService.detailByAppId(appid);
//                DyMiniLoginVO.Data dyData = DyMiniApiUtil.jscode2session(DyMiniLoginDTO.builder()
//                        .appid(appid)
//                        .secret(sysAppConfigBO.getAppSecret())
//                        .code(code)
//                        .anonymous_code(params.getAnonymousCode())
//                        .build());

                // 定制代开发
                // 拿到小程序appid信息
                SysAppServiceConfigDetailVO sysAppServiceConfigDetailVO = this.iSysAppServiceConfigService.detail();
                String component_appid = sysAppServiceConfigDetailVO.getComponentAppId();
                String component_appsecret = sysAppServiceConfigDetailVO.getComponentAppSecret();
                String component_access_token = DyServiceApiUtil.component_access_token(sysAppServiceConfigDetailVO.getId(), component_appid, component_appsecret);
                String authorizer_access_token = DyServiceApiUtil.authorizer_access_token(component_appid, component_access_token, DyServiceApiUtil.retrieve_authorization_code(component_appid, component_access_token, params.getAppId()));
                DyServiceLoginVO.Data dyData = DyServiceApiUtil.code2session(DyServiceLoginDTO.builder()
                        .component_appid(component_appid)
                        .authorizer_access_token(authorizer_access_token)
                        .code(code)
                        .anonymous_code(params.getAnonymousCode())
                        .build());

                openid = dyData.getOpenid();
                unionid = dyData.getUnionid();
                sessionKey = dyData.getSession_key();
                break;
            default:
                break;
        }

        // 先查询数据库中有没有openid
        UmsUser umsUser = this.umsUserMapper.selectOne(new LambdaQueryWrapper<UmsUser>().eq(UmsUser::getOpenid, openid).last(MybatisConstant.LIMIT_ONE));
        if (umsUser == null) {
            // 注册用户
            Long id = IdGeneratorUtil.nextId();
            umsUser = UmsUser.builder()
                    .id(id)
                    .type(type)
                    .openid(openid)
                    .unionid(unionid)
                    .phone(null)
                    .sex(UserSexEnum.未知.getType())
                    .birthday(null)
                    .appId(appid)
                    .build();
            UmsUserInfoDTO userInfo = params.getUserInfo();
            if (userInfo != null) {
                umsUser.setNickname(userInfo.getNickname());
                umsUser.setAvatarUrl(userInfo.getAvatarUrl());
            }
            umsUser.insert();
        }

        Long userId = umsUser.getId();
        UmsUserVO result = this.getUser(userId);

        if (StrUtil.isNotBlank(sessionKey)) {
            RedisUtil.set(UmsConstant.USER_SESSION_KEY + userId, sessionKey);
        }

        // 登录认证
        AuthLoginVO authLoginVO = AuthUtil.login(
                JwtUserBO.builder()
                        .authSourceEnum(AuthSourceEnum.C)
                        .userId(String.valueOf(userId))
                        .openid(openid)
                        .username(umsUser.getNickname())
                        .roleCodeList(Lists.newArrayList())
                        .build()
        );
        result.setTokenName(authLoginVO.getTokenName());
        result.setTokenValue(authLoginVO.getTokenValue());
        result.setSysUserId(this.iSysUserService.getUserIdByMiniUserId(userId));
        return result;
    }

    private UmsUserVO getLocalLogin() {
        UmsUserVO result = TenantUtil.executeRemoveFlag(() -> this.getUser(1L));
        // 登录认证
        AuthLoginVO authLoginVO = AuthUtil.login(
                JwtUserBO.builder()
                        .authSourceEnum(AuthSourceEnum.C)
                        .userId(String.valueOf(result.getId()))
                        .openid(result.getOpenid())
                        .username(result.getNickname())
                        .roleCodeList(Lists.newArrayList())
                        .build()
        );
        result.setTokenName(authLoginVO.getTokenName());
        result.setTokenValue(authLoginVO.getTokenValue());
        result.setSysUserId(TenantUtil.executeRemoveFlag(() -> this.iSysUserService.getUserIdByMiniUserId(1L)));
        return result;
    }

    @Override
    @SneakyThrows(Exception.class)
    public String bindPhone(UmsUserBindPhoneDTO params) {
        // 微信
//        WxMaPhoneNumberInfo phoneNoInfo = this.wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
//        WxMaPhoneNumberInfo newPhoneNoInfo = this.wxMaService.getUserService().getNewPhoneNoInfo(code);

        // 抖音
        Long currentUserId = params.getCurrentUserId();
        String decryptStr = DyMiniApiUtil.decrypt(params.getEncryptedData(), params.getIv(), RedisUtil.get(UmsConstant.USER_SESSION_KEY + currentUserId));
        String phone = DyMiniApiUtil.getPhone(decryptStr);
        UmsUser umsUser = this.detail(currentUserId);
        umsUser.setPhone(phone);
        umsUser.updateById();
        return phone;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UmsUserInfoDTO params) {
        this.umsUserMapper.updateUserInfo(params);
    }

    @Override
    public IPage<WebUmsUserPageVO> page(WebUmsUserPageDTO params) {
        return this.umsUserMapper.selectWebPage(new Page<>(), params);
    }

}
