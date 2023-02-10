package com.zhengqing.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.enums.YesNoEnum;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyBeanUtil;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.system.config.SystemProperty;
import com.zhengqing.system.entity.SysOauth;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.enums.SysDictTypeEnum;
import com.zhengqing.system.enums.SysOauthTypeEnum;
import com.zhengqing.system.mapper.SysOauthMapper;
import com.zhengqing.system.model.bo.SysGitHubUserInfoBO;
import com.zhengqing.system.model.bo.SysGiteeUserInfoBO;
import com.zhengqing.system.model.bo.SysQQUserInfoBO;
import com.zhengqing.system.model.bo.SysThirdpartOauthUserInfoBO;
import com.zhengqing.system.model.dto.SysOauthListDTO;
import com.zhengqing.system.model.dto.SysOauthRemoveBindDTO;
import com.zhengqing.system.model.dto.SysOauthSaveDTO;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.model.vo.SysOauthDataListVO;
import com.zhengqing.system.model.vo.SysOauthListVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysOauthService;
import com.zhengqing.system.service.ISysUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理 - 用户三方授权表 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:14
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysOauthServiceImpl extends ServiceImpl<SysOauthMapper, SysOauth> implements ISysOauthService {

    @Resource
    private SysOauthMapper sysOauthMapper;

    @Resource
    private SystemProperty systemProperty;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysDictService dictService;

    @Override
    @SneakyThrows(Exception.class)
    public String handleOauth(String oauthType, HttpServletResponse response) {
        AuthRequest authRequest = this.getAuthRequest(oauthType);
        String redirectUrl = authRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(redirectUrl);
        log.info("《三方授权》 重定向url：{}", redirectUrl);
        return redirectUrl;
    }

    @Override
    @SneakyThrows(Exception.class)
    public void handleCallback(String oauthType, AuthCallback callback, HttpServletResponse response) {
        if (oauthType.endsWith("Bind")) {
            this.handleCallbackBind(oauthType, callback, response);
            return;
        }
        Map<String, Object> authResponseMap = this.handleCallbackData(oauthType, callback);
        SysThirdpartOauthUserInfoBO oauthUserInfoBO = new SysThirdpartOauthUserInfoBO();
        switch (SysOauthTypeEnum.getEnum(oauthType)) {
            case Gitee:
                SysGiteeUserInfoBO sysGiteeUserInfoBO =
                        MyBeanUtil.mapToObj(authResponseMap, SysGiteeUserInfoBO.class);
                log.info("《三方授权》 用户信息： {}", sysGiteeUserInfoBO);
                oauthUserInfoBO.setOauthType(SysOauthTypeEnum.Gitee.getOauthTypeValue());
                oauthUserInfoBO.setOpenId(sysGiteeUserInfoBO.getUuid());
                oauthUserInfoBO.setUsername(sysGiteeUserInfoBO.getUsername());
                oauthUserInfoBO.setNickname(sysGiteeUserInfoBO.getNickname());
                oauthUserInfoBO.setAvatar(sysGiteeUserInfoBO.getAvatar());
                oauthUserInfoBO.setEmail(sysGiteeUserInfoBO.getEmail());
                oauthUserInfoBO.setSex(UserSexEnum.未知.getType());
                oauthUserInfoBO.setRemark(sysGiteeUserInfoBO.getRemark());
                break;
            case GitHub:
                SysGitHubUserInfoBO sysGitHubUserInfoBO =
                        MyBeanUtil.mapToObj(authResponseMap, SysGitHubUserInfoBO.class);
                log.info("《三方授权》 用户信息： {}", sysGitHubUserInfoBO);
                oauthUserInfoBO.setOauthType(SysOauthTypeEnum.GitHub.getOauthTypeValue());
                oauthUserInfoBO.setOpenId(sysGitHubUserInfoBO.getUuid());
                oauthUserInfoBO.setUsername(sysGitHubUserInfoBO.getUsername());
                oauthUserInfoBO.setNickname(sysGitHubUserInfoBO.getNickname());
                oauthUserInfoBO.setAvatar(sysGitHubUserInfoBO.getAvatar());
                oauthUserInfoBO.setEmail(sysGitHubUserInfoBO.getEmail());
                oauthUserInfoBO.setSex(UserSexEnum.未知.getType());
                oauthUserInfoBO.setRemark(sysGitHubUserInfoBO.getRemark());
                break;
            case QQ:
                SysQQUserInfoBO sysQqUserInfoBO = MyBeanUtil.mapToObj(authResponseMap, SysQQUserInfoBO.class);
                log.info("《三方授权》 用户信息： {}", sysQqUserInfoBO);
                oauthUserInfoBO.setOauthType(SysOauthTypeEnum.QQ.getOauthTypeValue());
                oauthUserInfoBO.setOpenId(sysQqUserInfoBO.getUuid());
                oauthUserInfoBO.setUsername(sysQqUserInfoBO.getUsername());
                oauthUserInfoBO.setNickname(sysQqUserInfoBO.getNickname());
                oauthUserInfoBO.setAvatar(sysQqUserInfoBO.getAvatar());
                oauthUserInfoBO.setEmail(sysQqUserInfoBO.getEmail());
                oauthUserInfoBO.setSex(UserSexEnum.未知.getType());
                oauthUserInfoBO.setRemark(sysQqUserInfoBO.getRemark());
                break;
            default:
                break;
        }

        Integer userId = this.handleOauthThirdPartData(oauthUserInfoBO);
        SysUser sysUser = new SysUser().selectById(userId);
//        // 塞个token信息
//        JwtUserBO jwtUserBO = JwtUserBO.buildUser(JSONObject.parseObject(JSONObject.toJSONString(sysUser), Map.class));
//        String jwtToken = JwtUtil.buildJWT(jwtUserBO);
//        sysUser.setToken(jwtToken);
//        sysUser.updateById();
        String jwtToken = "token...";

        String redirectUrl = this.systemProperty.getThirdpartOauth().getWebRedirectUrl() + jwtToken;
        response.sendRedirect(redirectUrl);
        log.info("《三方授权》 授权回调地址： {}", redirectUrl);
    }

    @Override
    @SneakyThrows(Exception.class)
    public void handleCallbackBind(String oauthType, AuthCallback callback, HttpServletResponse response) {
        Map<String, Object> authResponseMap = this.handleCallbackData(oauthType, callback);
        String openId = String.valueOf(authResponseMap.get("uuid"));
        String redirectUrl = String.format(this.systemProperty.getThirdpartOauth().getWebBindRedirectUrl(),
                SysOauthTypeEnum.getEnum(oauthType).getOauthTypeValue(), openId);
        response.sendRedirect(redirectUrl);
        log.info("《三方授权》 绑定回调地址： {}", redirectUrl);
    }

    /**
     * 处理回调数据
     *
     * @param oauthType: 授权数据类型
     * @param callback   回调信息
     * @return 回调响应map数据
     * @author zhengqingya
     * @date 2020/12/6 18:48
     */
    private Map<String, Object> handleCallbackData(String oauthType, AuthCallback callback) {
        AuthRequest authRequest = this.getAuthRequest(oauthType);
        AuthResponse authResponse = authRequest.login(callback);
        int code = authResponse.getCode();
        if (code != 2000) {
            throw new MyException("《三方授权》 回调异常： " + authResponse.getMsg());
        }
        Object authResponseData = authResponse.getData();
        Map<String, Object> authResponseMap =
                JSONObject.parseObject(JSONObject.toJSONString(authResponseData), Map.class);
        log.debug("《三方授权》 授权信息：{}", authResponseMap);
        return authResponseMap;
    }

    private AuthRequest getAuthRequest(String oauthType) {
        switch (SysOauthTypeEnum.getEnum(oauthType)) {
            case Gitee:
                return new AuthGiteeRequest(this.systemProperty.getThirdpartOauth().getGitee());
            case GiteeBind:
                return new AuthGiteeRequest(this.systemProperty.getThirdpartOauth().getGiteeBind());
            case GitHub:
                return new AuthGithubRequest(this.systemProperty.getThirdpartOauth().getGithub());
            case GitHubBind:
                return new AuthGithubRequest(this.systemProperty.getThirdpartOauth().getGithubBind());
            case QQ:
                return new AuthQqRequest(this.systemProperty.getThirdpartOauth().getQq());
            default:
                throw new MyException("未找到存在的授权数据类型！");
        }
    }

    /**
     * 处理授权数据
     *
     * @param params:
     * @return java.lang.Integer
     * @author zhengqingya
     * @date 2020/12/6 19:03
     */
    private Integer handleOauthThirdPartData(SysThirdpartOauthUserInfoBO params) {
        Integer oauthType = params.getOauthType();
        String openId = params.getOpenId();
        String username = params.getUsername();
        String nickname = params.getNickname();
        String avatar = params.getAvatar();
        String email = params.getEmail();
        Byte sex = params.getSex();
        String remark = params.getRemark();

        SysOauth oauthInfo = this.sysOauthMapper.selectOne(
                new LambdaQueryWrapper<SysOauth>().eq(SysOauth::getOpenId, openId).eq(SysOauth::getOauthType, oauthType));
        if (oauthInfo != null) {
            return oauthInfo.getUserId();
        }

        // 新增一个用户，然后拿到用户id，之后登录进系统进行用户其它信息补全...
        SysUserSaveDTO userSaveDTO = new SysUserSaveDTO();
        // TODO 账号暂不填！！！
        userSaveDTO.setUsername("");
        userSaveDTO.setNickname(nickname);
        userSaveDTO.setSex(sex);
        userSaveDTO.setEmail(email);
        userSaveDTO.setAvatarUrl(avatar);

        Integer userId = this.sysUserService.addOrUpdateData(userSaveDTO);

        SysOauthSaveDTO oauthSaveDTO = new SysOauthSaveDTO();
        oauthSaveDTO.setUserId(userId);
        oauthSaveDTO.setOpenId(openId);
        oauthSaveDTO.setOauthType(oauthType);
        this.addOrUpdateData(oauthSaveDTO);
        return userId;
    }

    @Override
    public Integer addOrUpdateData(SysOauthSaveDTO params) {
        Integer userId = params.getUserId();
        String openId = params.getOpenId();
        Integer oauthType = params.getOauthType();
        SysOauth oauthInfo = this.sysOauthMapper.selectOne(
                new LambdaQueryWrapper<SysOauth>().eq(SysOauth::getOpenId, openId).eq(SysOauth::getOauthType, oauthType));
        if (oauthInfo != null) {
            return oauthInfo.getUserId();
        }
        oauthInfo = new SysOauth();
        oauthInfo.setUserId(userId);
        oauthInfo.setOpenId(openId);
        oauthInfo.setOauthType(oauthType);
        oauthInfo.insert();
        return oauthInfo.getUserReOauthId();
    }

    @Override
    public List<SysOauthListVO> list(SysOauthListDTO params) {
        return this.sysOauthMapper.selectDataList(params);
    }

    @Override
    public List<SysOauthDataListVO> getOauthDataList(Integer userId) {
        List<SysOauthDataListVO> oauthDataList = Lists.newArrayList();
        List<SysDictVO> oauthTypeList = this.dictService.listFromCacheByCode(Lists.newArrayList(SysDictTypeEnum.第三方帐号授权类型.getCode())).get(SysDictTypeEnum.第三方帐号授权类型.getCode());
        List<SysOauthListVO> oauthBindList = this.list(SysOauthListDTO.builder().userId(userId).build());
        List<Integer> oauthTypeBindList =
                oauthBindList.stream().map(SysOauthListVO::getOauthType).collect(Collectors.toList());
        Map<Integer, Integer> oauthBindMap = oauthBindList.stream()
                .collect(Collectors.toMap(SysOauthListVO::getOauthType, SysOauthListVO::getUserReOauthId, (k1, k2) -> k1));
        oauthTypeList.forEach(oauthTypeItem -> {
            String oauthTypeName = oauthTypeItem.getName();
            Integer oauthTypeValue = Integer.valueOf(oauthTypeItem.getValue());
            SysOauthDataListVO oauthData = new SysOauthDataListVO();
            oauthData.setOauthType(oauthTypeValue);
            oauthData.setOauthTypeName(oauthTypeName);
            oauthData.setOauthTypeBindName(oauthTypeName + "Bind");
            oauthData.setOauthTypeDesc(SysOauthTypeEnum.getEnum(oauthTypeName).getDesc());
            oauthData.setIfBind(
                    oauthTypeBindList.contains(oauthTypeValue) ? YesNoEnum.YES.getValue() : YesNoEnum.NO.getValue());
            oauthData.setUserReOauthId(oauthBindMap.get(oauthTypeValue));
            oauthDataList.add(oauthData);
        });
        return oauthDataList;
    }

    @Override
    public void removeBind(SysOauthRemoveBindDTO params) {
        Integer userReOauthId = params.getUserReOauthId();
        this.removeById(userReOauthId);
    }

}
