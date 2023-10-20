package com.zhengqing.ums.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.lang.Assert;
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
import com.zhengqing.common.sdk.douyin.mini.model.dto.DyMiniLoginDTO;
import com.zhengqing.common.sdk.douyin.mini.model.vo.DyMiniLoginVO;
import com.zhengqing.common.sdk.douyin.mini.util.DyMiniApiUtil;
import com.zhengqing.system.model.bo.SysAppConfigBO;
import com.zhengqing.system.service.ISysAppConfigService;
import com.zhengqing.ums.entity.UmsUser;
import com.zhengqing.ums.enums.MiniTypeEnum;
import com.zhengqing.ums.factory.WxMaFactory;
import com.zhengqing.ums.mapper.UmsUserMapper;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.dto.UmsUserInfoDTO;
import com.zhengqing.ums.model.dto.UmsUserLoginDTO;
import com.zhengqing.ums.model.dto.WebUmsUserPageDTO;
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
        switch (MiniTypeEnum.getEnum(type)) {
            case 微信小程序:
                WxMaJscode2SessionResult wxMaJscode2SessionResult = this.wxMaFactory.wxMaService().jsCode2SessionInfo(code);
                openid = wxMaJscode2SessionResult.getOpenid();
                break;
            case 抖音小程序:
//                String component_appid = params.getComponent_appid();
//                String component_appsecret = params.getComponent_appsecret();
//                String component_access_token = DyServiceApiUtil.component_access_token(component_appid, component_appsecret);
//                String authorizer_access_token = DyServiceApiUtil.authorizer_access_token(component_appid,
//                        component_access_token,
//                        DyServiceApiUtil.retrieve_authorization_code(component_appid, component_access_token, appid)
//                );
//                DyServiceLoginVO.Data dyData = DyServiceApiUtil.code2session(DyServiceLoginDTO.builder()
//                        .component_appid(component_appid)
//                        .authorizer_access_token(authorizer_access_token)
//                        .code(code)
//                        .anonymous_code(params.getAnonymousCode())
//                        .build());
                SysAppConfigBO sysAppConfigBO = this.iSysAppConfigService.detailByAppId(appid);
                DyMiniLoginVO.Data dyData = DyMiniApiUtil.jscode2session(DyMiniLoginDTO.builder()
                        .appid(appid)
                        .secret(sysAppConfigBO.getAppSecret())
                        .code(code)
                        .anonymous_code(params.getAnonymousCode())
                        .build());
                openid = dyData.getOpenid();
                unionid = dyData.getUnionid();
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
                    .build();
            UmsUserInfoDTO userInfo = params.getUserInfo();
            if (userInfo != null) {
                umsUser.setNickname(userInfo.getNickname());
                umsUser.setAvatarUrl(userInfo.getAvatarUrl());
            }
            umsUser.insert();
        }

        UmsUserVO result = this.getUser(umsUser.getId());

        // 登录认证
        AuthLoginVO authLoginVO = AuthUtil.login(
                JwtUserBO.builder()
                        .authSourceEnum(AuthSourceEnum.C)
                        .userId(String.valueOf(umsUser.getId()))
                        .openid(openid)
                        .username(umsUser.getNickname())
                        .roleCodeList(Lists.newArrayList())
                        .build()
        );
        result.setTokenName(authLoginVO.getTokenName());
        result.setTokenValue(authLoginVO.getTokenValue());
        return result;
    }

    private UmsUserVO getLocalLogin() {
        UmsUserVO result = this.getUser(1L);
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
        return result;
    }

    @Override
    @SneakyThrows(Exception.class)
    public UmsUserVO getPhone(String code) {
//        WxMaPhoneNumberInfo phoneNoInfo = this.wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
//        WxMaPhoneNumberInfo newPhoneNoInfo = this.wxMaService.getUserService().getNewPhoneNoInfo(code);
        return null;
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
