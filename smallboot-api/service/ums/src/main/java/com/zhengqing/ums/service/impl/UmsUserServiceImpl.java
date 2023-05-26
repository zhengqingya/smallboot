package com.zhengqing.ums.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.enums.AuthSourceEnum;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.ums.entity.UmsUser;
import com.zhengqing.ums.factory.WxMaFactory;
import com.zhengqing.ums.mapper.UmsUserMapper;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.dto.UmsUserWxLoginDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.service.IUmsUserService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService {

    private UmsUserMapper umsUserMapper;

    private WxMaFactory wxMaFactory;

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
    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows(Exception.class)
    public UmsUserVO wxLogin(UmsUserWxLoginDTO params) {
        if (params.getIsLocalLogin()) {
            return this.getLocalLogin();
        }

        String code = params.getCode();
        WxMaJscode2SessionResult wxMaJscode2SessionResult = this.wxMaFactory.wxMaService().jsCode2SessionInfo(code);
        String openid = wxMaJscode2SessionResult.getOpenid();

        // 先查询数据库中有没有openid
        UmsUser umsUser = this.umsUserMapper.selectOne(
                new LambdaQueryWrapper<UmsUser>()
                        .eq(UmsUser::getOpenid, openid)
                        .last(MybatisConstant.LIMIT_ONE)
        );
        if (umsUser == null) {
            // 注册用户
            UmsUserWxLoginDTO.WxUserInfo userInfo = params.getUserInfo();
            Long id = IdGeneratorUtil.nextId();
            umsUser = UmsUser.builder()
                    .id(id)
                    .openid(openid)
                    .nickname(userInfo.getNickName())
                    .phone(null)
                    .sex(UserSexEnum.未知.getType())
                    .birthday(null)
                    .avatarUrl(userInfo.getAvatarUrl())
                    .build();
            umsUser.insert();
        }

        UmsUserVO result = this.getUser(umsUser.getId());

        // 登录认证
        StpUtil.login(JSONUtil.toJsonStr(
                JwtUserBO.builder()
                        .authSourceEnum(AuthSourceEnum.C)
                        .userId(String.valueOf(umsUser.getId()))
                        .openid(openid)
                        .username(umsUser.getNickname())
                        .roleCodeList(Lists.newArrayList())
                        .build()
        ));
        result.setTokenName(StpUtil.getTokenName());
        result.setTokenValue(StpUtil.getTokenValue());
        return result;
    }

    private UmsUserVO getLocalLogin() {
        UmsUserVO result = this.getUser(1L);
        // 登录认证
        StpUtil.login(JSONUtil.toJsonStr(
                JwtUserBO.builder()
                        .authSourceEnum(AuthSourceEnum.C)
                        .userId(String.valueOf(result.getId()))
                        .openid(result.getOpenid())
                        .username(result.getNickname())
                        .roleCodeList(Lists.newArrayList())
                        .build()
        ));
        result.setTokenName(StpUtil.getTokenName());
        result.setTokenValue(StpUtil.getTokenValue());
        return result;
    }

    @Override
    @SneakyThrows(Exception.class)
    public UmsUserVO getPhone(String code) {
//        WxMaPhoneNumberInfo phoneNoInfo = this.wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
//        WxMaPhoneNumberInfo newPhoneNoInfo = this.wxMaService.getUserService().getNewPhoneNoInfo(code);
        return null;
    }


}
