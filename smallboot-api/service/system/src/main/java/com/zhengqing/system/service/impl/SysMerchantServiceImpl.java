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
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.enums.CommonStatusEnum;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.AutoUpgradeVersionUtil;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.db.util.TenantUtil;
import com.zhengqing.common.sdk.douyin.service.util.DyServiceApiUtil;
import com.zhengqing.system.config.SystemProperty;
import com.zhengqing.system.entity.SysMerchant;
import com.zhengqing.system.enums.SysAppStatusEnum;
import com.zhengqing.system.enums.SysConfigKeyEnum;
import com.zhengqing.system.enums.SysRoleCodeEnum;
import com.zhengqing.system.enums.SysVersionTypeEnum;
import com.zhengqing.system.mapper.SysMerchantMapper;
import com.zhengqing.system.model.bo.SysExtJsonBO;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysMerchantListVO;
import com.zhengqing.system.model.vo.SysMerchantPageVO;
import com.zhengqing.system.model.vo.SysVersionBaseVO;
import com.zhengqing.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p> 系统管理-商户管理 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMerchantServiceImpl extends ServiceImpl<SysMerchantMapper, SysMerchant> implements ISysMerchantService {

    private final SysMerchantMapper sysMerchantMapper;
    private final ISysPermBusinessService iSysPermBusinessService;
    private final ISysUserService iSysUserService;
    private final ISysRoleService iSysRoleService;
    private final ISysConfigService iSysConfigService;
    private final ISysVersionService iSysVersionService;
    private final SystemProperty systemProperty;

    @Override
    public IPage<SysMerchantPageVO> page(SysMerchantPageDTO params) {
        IPage<SysMerchantPageVO> result = this.sysMerchantMapper.selectDataPage(new Page<>(), params);
        List<SysMerchantPageVO> list = result.getRecords();
        list.forEach(SysMerchantPageVO::handleData);
        return result;
    }

    @Override
    public List<SysMerchantListVO> list(SysMerchantListDTO params) {
        return this.sysMerchantMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysMerchantSaveDTO params) {
        Integer id = params.getId();
        boolean isAdd = id == null;
        String name = params.getName();
        Integer customId = params.getCustomId();

        // 校验名称是否重复
        SysMerchant oldData = this.sysMerchantMapper.selectOne(new LambdaQueryWrapper<SysMerchant>().eq(SysMerchant::getName, name).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(oldData == null || oldData.getId().equals(id), "名称重复，请重新输入！");

        SysMerchant sysMerchant = SysMerchant.builder()
                .id(id)
                .name(name)
                .sort(params.getSort())
                .phone(params.getPhone())
                .email(params.getEmail())
                .status(params.getStatus())
                .remark(params.getRemark())
                .type(params.getType())
                .expireTime(params.getExpireTime())
                .userNum(params.getUserNum())
                .jobNum(params.getJobNum())
                .appId(params.getAppId())
                .appSecret(params.getAppSecret())
                .appIndexTitle(params.getAppIndexTitle())
                .build();
        if (isAdd && customId != null) {
            sysMerchant.setId(customId);
            // 看下自定义商户id有没有重复的
            TenantUtil.execute(() -> Assert.isNull(this.sysMerchantMapper.selectById(customId), "商户ID重复，请重新输入！"));
        }
        sysMerchant.insertOrUpdate();

        if (isAdd) {
            // 创建商户关联的用户 & 分配角色权限
            // 查询商户管理员角色id
            Integer roleId = this.iSysRoleService.getRoleIdByCode(SysRoleCodeEnum.商户管理员);

            // 创建用户
            Integer userId = this.iSysUserService.addOrUpdateData(SysUserSaveDTO.builder()
                    .username(params.getUsername())
                    .nickname(params.getName())
                    .password(params.getPassword())
                    .phone(params.getPhone())
                    .roleIdList(Lists.newArrayList(roleId))
                    .isFixed(true)
                    .merchantId(sysMerchant.getId())
                    .isMerchantAdmin(true)
                    .build());
            sysMerchant.setAdminUserId(userId);
            sysMerchant.updateById();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.sysMerchantMapper.deleteById(id);
    }

    @Override
    public SysMerchant checkData(Integer id) {
        SysMerchant sysMerchant = this.detail(id);
        Assert.isTrue(Objects.equals(CommonStatusEnum.ENABLE.getStatus(), sysMerchant.getStatus()), "商户已停用！");
        Date expireTime = sysMerchant.getExpireTime();
        Assert.isTrue(expireTime.after(new Date()), "限制：商户服务已到期！过期时间：" + MyDateUtil.dateToStr(expireTime));
        return sysMerchant;
    }

    @Override
    public SysMerchant detail(Integer id) {
        Assert.notNull(id, "该商户不存在！");
        SysMerchant sysMerchant = this.sysMerchantMapper.selectById(id);
        Assert.notNull(sysMerchant, "该商户不存在！");
        return sysMerchant;
    }

    @Override
    public String genLink() {
        // 拿到小程序appid信息
        String component_appid = String.valueOf(this.iSysConfigService.getValue(SysConfigKeyEnum.DOUYIN_COMPONENT_APPID));
        String component_appsecret = String.valueOf(this.iSysConfigService.getValue(SysConfigKeyEnum.DOUYIN_COMPONENT_APPSECRET));
        String component_access_token = DyServiceApiUtil.component_access_token(component_appid, component_appsecret);
        return DyServiceApiUtil.gen_link(component_appid, component_access_token, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appOperationBatch(SysMerchantAppOperationDTO params) {
        log.info("[系统管理] 批量操作(小程序提审、发布)：{}", JSONUtil.toJsonStr(params));
        List<Integer> idList = params.getIdList();
        String uploadCodeDesc = params.getUploadCodeDesc();
        Integer templateId = params.getTemplateId();
        Integer appStatus = params.getAppStatus();
        SysAppStatusEnum appStatusEnum = SysAppStatusEnum.getEnum(appStatus);

        Integer latelyVersionId = null;
        String versionNew = "0.0.1";
        SysVersionBaseVO latelyVersion = this.iSysVersionService.lately();
        if (SysAppStatusEnum.提交代码 != appStatusEnum) {
            Assert.notNull(latelyVersion, "请先提交代码！");
            latelyVersionId = latelyVersion.getId();
        }

        List<SysMerchant> sysMerchantList;
        if (CollUtil.isEmpty(idList)) {
            sysMerchantList = this.sysMerchantMapper.selectAppIdList();
        } else {
            sysMerchantList = this.sysMerchantMapper.selectBatchIds(idList);
        }

        // 拿到小程序appid信息
        String component_appid = String.valueOf(this.iSysConfigService.getValue(SysConfigKeyEnum.DOUYIN_COMPONENT_APPID));
        String component_appsecret = String.valueOf(this.iSysConfigService.getValue(SysConfigKeyEnum.DOUYIN_COMPONENT_APPSECRET));

        String component_access_token = DyServiceApiUtil.component_access_token(component_appid, component_appsecret);
        for (SysMerchant item : sysMerchantList) {
            Integer merchantId = item.getId();
            String appId = item.getAppId();
            String appSecret = item.getAppSecret();
            String appIndexTitle = item.getAppIndexTitle();
            String authorizer_access_token = DyServiceApiUtil.authorizer_access_token(component_appid, component_access_token, DyServiceApiUtil.retrieve_authorization_code(component_appid, component_access_token, appId));
            switch (appStatusEnum) {
                case 提交代码:
                    if (latelyVersion != null) {
                        versionNew = AutoUpgradeVersionUtil.autoUpgradeVersion(latelyVersion.getVersion());
                    }
                    String ext_json = JSONUtil.toJsonStr(
                            SysExtJsonBO.builder()
                                    .extAppid(appId)
                                    .extPages(new HashMap<String, Object>() {{
                                        this.put("pages/index/index",
                                                StrUtil.format("{\"navigationBarTitleText\": \"{}\"}",
                                                        StrUtil.isBlank(appIndexTitle) ? "首页" : appIndexTitle)
                                        );
                                    }})
                                    .ext(
                                            SysExtJsonBO.Ext.builder()
                                                    .tenantId(String.valueOf(TenantIdContext.getTenantId()))
                                                    .merchantId(String.valueOf(merchantId))
                                                    .appId(appId)
                                                    .appSecret(appSecret)
                                                    .baseUrl(this.systemProperty.getServiceApi())
                                                    .build()
                                    )
                                    .build()
                    );
                    DyServiceApiUtil.uploadCode(component_appid, authorizer_access_token, templateId, uploadCodeDesc, versionNew, ext_json);
                    break;
                case 提审代码:
                    DyServiceApiUtil.audit(component_appid, authorizer_access_token);
                    break;
                case 发布代码:
                    DyServiceApiUtil.release(component_appid, authorizer_access_token);
                    break;
                default:
                    throw new MyException("不支持操作！");
            }
        }

        // 保存版本记录
        this.iSysVersionService.addOrUpdateData(SysVersionSaveDTO.builder()
                .id(latelyVersionId)
                .status(appStatus)
                .type(SysVersionTypeEnum.抖音代开发小程序.getType())
                .name(uploadCodeDesc)
                .remark(appStatusEnum.getDesc())
                .build());
    }
}
