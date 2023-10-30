package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.db.util.TenantUtil;
import com.zhengqing.common.sdk.douyin.service.model.vo.DyServiceVersionVO;
import com.zhengqing.common.sdk.douyin.service.util.DyServiceApiUtil;
import com.zhengqing.system.entity.SysAppConfig;
import com.zhengqing.system.enums.SysAppStatusEnum;
import com.zhengqing.system.mapper.SysAppConfigMapper;
import com.zhengqing.system.model.bo.SysAppConfigBO;
import com.zhengqing.system.model.bo.SysExtJsonBO;
import com.zhengqing.system.model.dto.SysAppConfigDTO;
import com.zhengqing.system.model.dto.SysAppOperationDTO;
import com.zhengqing.system.model.dto.SysAppQrcodeDTO;
import com.zhengqing.system.model.vo.SysAppServiceConfigDetailVO;
import com.zhengqing.system.service.ISysAppConfigService;
import com.zhengqing.system.service.ISysAppServiceConfigService;
import com.zhengqing.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 系统管理-小程序配置 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/17 21:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAppConfigServiceImpl extends ServiceImpl<SysAppConfigMapper, SysAppConfig> implements ISysAppConfigService {

    private final SysAppConfigMapper sysAppConfigMapper;
    private final ISysConfigService iSysConfigService;
    private final ISysAppServiceConfigService iSysAppServiceConfigService;

    @Override
    public IPage<SysAppConfigBO> page(SysAppConfigDTO params) {
        return this.sysAppConfigMapper.selectDataList(new Page<>(), params);
    }

    @Override
    public List<SysAppConfigBO> list(SysAppConfigDTO params) {
        return this.sysAppConfigMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysAppConfigBO params) {
        Integer tenantId = params.getTenantId();
        String appId = params.getAppId();
        Integer id = params.getId();

        if (StrUtil.isNotBlank(appId)) {
            // 校验appId是否重复
            SysAppConfig sysAppConfigOld = TenantUtil.executeRemoveFlag(() -> this.sysAppConfigMapper.selectOne(new LambdaQueryWrapper<SysAppConfig>().eq(SysAppConfig::getAppId, appId).last(MybatisConstant.LIMIT_ONE)));
            Assert.isTrue(sysAppConfigOld == null || sysAppConfigOld.getId().equals(id), "AppID重复，请重新输入！");
        }

        SysAppConfig sysAppConfig = SysAppConfig.builder()
                .id(id)
                .tenantId(tenantId)
                .name(params.getName())
                .appId(appId)
                .appSecret(params.getAppSecret())
                .appVersion(params.getAppVersion())
                .appVersionObj(params.getAppVersionObj())
                .appStatus(params.getAppStatus())
                .appIndexTitle(params.getAppIndexTitle())
                .appType(params.getAppType())
                .build();

        TenantUtil.executeRemoveFlag(sysAppConfig::insertOrUpdate);
        return sysAppConfig.getId();
    }

    @Override
    public List<SysAppConfigBO> getAllUsableAppConfig() {
        return this.list(SysAppConfigDTO.builder().isUsable(true).build());
    }

    @Override
    public Map<Integer, SysAppConfigBO> mapByTenantIdList(List<Integer> tenantIdList) {
        List<SysAppConfigBO> list = this.list(SysAppConfigDTO.builder().tenantIdList(tenantIdList).build());
        return list.stream().collect(Collectors.toMap(SysAppConfigBO::getTenantId, t -> t, (oldData, newData) -> newData));
    }

    @Override
    public Map<String, SysAppConfigBO> mapByAppIdList(List<String> appIdList) {
        List<SysAppConfigBO> list = this.list(SysAppConfigDTO.builder().appIdList(appIdList).build());
        return list.stream().collect(Collectors.toMap(SysAppConfigBO::getAppId, t -> t, (oldData, newData) -> newData));
    }

    @Override
    public SysAppConfigBO detailByAppId(String appId) {
        SysAppConfigBO sysAppConfigBO = this.mapByAppIdList(Lists.newArrayList(appId)).get(appId);
        Assert.notNull(sysAppConfigBO, "小程序配置丢失！");
        return sysAppConfigBO;
    }

    @Override
    public String genLink() {
        // 拿到小程序appid信息
        SysAppServiceConfigDetailVO sysAppServiceConfigDetailVO = this.iSysAppServiceConfigService.detail();
        String component_appid = sysAppServiceConfigDetailVO.getComponentAppId();
        String component_appsecret = sysAppServiceConfigDetailVO.getComponentAppSecret();
        String component_access_token = DyServiceApiUtil.component_access_token(sysAppServiceConfigDetailVO.getId(), component_appid, component_appsecret);
        return DyServiceApiUtil.gen_link(component_appid, component_access_token, null);
    }

    @Override
    public byte[] qrcode(SysAppQrcodeDTO params) {
        // 拿到小程序appid信息
        SysAppServiceConfigDetailVO sysAppServiceConfigDetailVO = this.iSysAppServiceConfigService.detail();
        String component_appid = sysAppServiceConfigDetailVO.getComponentAppId();
        String component_appsecret = sysAppServiceConfigDetailVO.getComponentAppSecret();
        String component_access_token = DyServiceApiUtil.component_access_token(sysAppServiceConfigDetailVO.getId(), component_appid, component_appsecret);
        String authorizer_access_token = DyServiceApiUtil.authorizer_access_token(component_appid, component_access_token, DyServiceApiUtil.retrieve_authorization_code(component_appid, component_access_token, params.getAppId()));
        return DyServiceApiUtil.qrcode(component_appid, authorizer_access_token, params.getVersion(), params.getPath());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operationBatch(SysAppOperationDTO params) {
        log.info("[系统管理] 批量操作(小程序提审、发布)：{}", JSONUtil.toJsonStr(params));
        List<String> appIdList = params.getAppIdList();
        String uploadCodeDesc = params.getUploadCodeDesc();
        String appVersion = params.getAppVersion();
        Integer templateId = params.getTemplateId();
        Integer appStatus = params.getAppStatus();
        Integer finallAppStatus = null;
        SysAppStatusEnum appStatusEnum = SysAppStatusEnum.getEnum(appStatus);

//        Integer latelyVersionId = null;
//        String versionNew = "0.0.1";
//        SysVersionBaseVO latelyVersion = this.iSysVersionService.lately();
//        if (SysAppStatusEnum.提交代码 != appStatusEnum) {
//            Assert.notNull(latelyVersion, "请先提交代码！");
//            latelyVersionId = latelyVersion.getId();
//        }

        List<SysAppConfigBO> appConfigList;
        if (CollUtil.isEmpty(appIdList)) {
            appConfigList = this.getAllUsableAppConfig();
        } else {
            appConfigList = this.list(SysAppConfigDTO.builder().appIdList(appIdList).build());
        }
        appConfigList = appConfigList.stream().filter(e -> StrUtil.isNotBlank(e.getAppId())).collect(Collectors.toList());
        Assert.notEmpty(appConfigList, "暂无可用的小程序配置！");

        // 拿到小程序appid信息
        SysAppServiceConfigDetailVO sysAppServiceConfigDetailVO = this.iSysAppServiceConfigService.detail();
        String component_appid = sysAppServiceConfigDetailVO.getComponentAppId();
        String component_appsecret = sysAppServiceConfigDetailVO.getComponentAppSecret();
        String component_access_token = DyServiceApiUtil.component_access_token(sysAppServiceConfigDetailVO.getId(), component_appid, component_appsecret);
        for (SysAppConfigBO appConfigItem : appConfigList) {
            Integer appReTenantId = appConfigItem.getTenantId();
            String appId = appConfigItem.getAppId();
            String appIndexTitle = appConfigItem.getAppIndexTitle();
            String authorizer_access_token = DyServiceApiUtil.authorizer_access_token(component_appid, component_access_token, DyServiceApiUtil.retrieve_authorization_code(component_appid, component_access_token, appId));
            switch (appStatusEnum) {
                case 提交代码:
//                    if (latelyVersion != null) {
//                        versionNew = AutoUpgradeVersionUtil.autoUpgradeVersion(latelyVersion.getVersion());
//                    }
                    String ext_json = JSONUtil.toJsonStr(
                            SysExtJsonBO.builder()
                                    .extEnable(true)
//                                    .directCommit(true)
                                    .extAppid(appId)
                                    .ext(
                                            SysExtJsonBO.Ext.builder()
                                                    .tenantId(String.valueOf(appReTenantId))
                                                    .appId(appId)
                                                    .build()
                                    )
                                    .extPages(new HashMap<String, Object>() {{
                                        this.put("pages/index/index",
                                                StrUtil.format("{\"navigationBarTitleText\": \"{}\"}",
                                                        StrUtil.isBlank(appIndexTitle) ? "首页" : appIndexTitle)
                                        );
                                    }})
                                    .build()
                    );
                    DyServiceApiUtil.uploadCode(component_appid, authorizer_access_token, templateId, uploadCodeDesc, appVersion, ext_json);
                    appConfigItem.setAppStatus(SysAppStatusEnum.提交代码.getStatus());
                    break;
                case 提审代码:
                    DyServiceApiUtil.audit(component_appid, authorizer_access_token);
                    appConfigItem.setAppStatus(SysAppStatusEnum.提审中.getStatus());
                    break;
                case 撤回审核:
                    DyServiceApiUtil.revoke_audit(component_appid, authorizer_access_token);
                    appConfigItem.setAppStatus(SysAppStatusEnum.撤回审核.getStatus());
                    break;
                case 发布代码:
                    DyServiceApiUtil.release(component_appid, authorizer_access_token);
                    appConfigItem.setAppStatus(SysAppStatusEnum.已发布.getStatus());
                    break;
                default:
                    throw new MyException("不支持操作！");
            }
            this.addOrUpdateData(appConfigItem);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncStatus() {
        List<SysAppConfigBO> appConfigList = this.getAllUsableAppConfig();
        Assert.notEmpty(appConfigList, "暂无可用的小程序配置！");

        // 拿到小程序appid信息
        SysAppServiceConfigDetailVO sysAppServiceConfigDetailVO = this.iSysAppServiceConfigService.detail();
        String component_appid = sysAppServiceConfigDetailVO.getComponentAppId();
        String component_appsecret = sysAppServiceConfigDetailVO.getComponentAppSecret();
        String component_access_token = DyServiceApiUtil.component_access_token(sysAppServiceConfigDetailVO.getId(), component_appid, component_appsecret);

        appConfigList.forEach(item -> {
            String appId = item.getAppId();
            String authorizer_access_token = DyServiceApiUtil.authorizer_access_token(component_appid, component_access_token, DyServiceApiUtil.retrieve_authorization_code(component_appid, component_access_token, appId));
            DyServiceVersionVO.Data versionObj = DyServiceApiUtil.versions(component_appid, authorizer_access_token);
            DyServiceVersionVO.CurrentVersion current = versionObj.getCurrent();
            if (current != null) {
                item.setAppVersion(current.getVersion());
            }
            item.setAppVersionObj(versionObj);
            // 保存
            this.addOrUpdateData(item);
        });
    }

}
