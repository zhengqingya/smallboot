package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.enums.ApiResultCodeEnum;
import com.zhengqing.common.base.enums.CommonStatusEnum;
import com.zhengqing.common.base.enums.SysRoleCodeEnum;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.db.util.TenantUtil;
import com.zhengqing.system.config.SystemProperty;
import com.zhengqing.system.entity.SysTenant;
import com.zhengqing.system.entity.SysTenantPackage;
import com.zhengqing.system.mapper.SysTenantMapper;
import com.zhengqing.system.model.bo.SysAppConfigBO;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysRoleBaseVO;
import com.zhengqing.system.model.vo.SysTenantConfigVO;
import com.zhengqing.system.model.vo.SysTenantListVO;
import com.zhengqing.system.model.vo.SysTenantPageVO;
import com.zhengqing.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p> 系统管理-租户信息 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 15:40
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

    private final SysTenantMapper sysTenantMapper;
    @Lazy
    @Resource
    private ISysTenantPackageService iSysTenantPackageService;
    private final ISysPermBusinessService iSysPermBusinessService;
    private final ISysUserService iSysUserService;
    private final ISysRoleService iSysRoleService;
    private final ISysAppConfigService iSysAppConfigService;
    private final ISysRoleMenuService iSysRoleMenuService;
    private final ISysRoleScopeService iSysRoleScopeService;
    private final SystemProperty systemProperty;

    @Override
    public SysTenant detail(Integer id) {
        SysTenant sysTenant = this.sysTenantMapper.selectById(id);
        Assert.notNull(sysTenant, "该租户不存在");
        return sysTenant;
    }

    @Override
    public SysTenantConfigVO configForApp(Integer id) {
        // 暂时主要是校验，不返回给小程序任何信息...
        try {
            SysTenant sysTenant = this.checkData(id);
            Map<Integer, SysAppConfigBO> appConfigMap = this.iSysAppConfigService.mapByTenantIdList(Lists.newArrayList(id));
            SysAppConfigBO sysAppConfigBO = appConfigMap.get(id);
            return SysTenantConfigVO.builder().build();
        } catch (Exception e) {
            throw new MyException(ApiResultCodeEnum.APP_SERVICE_ERROR.getCode(), e.getMessage());
        }
    }

    @Override
    public IPage<SysTenantPageVO> page(SysTenantPageDTO params) {
        IPage<SysTenantPageVO> result = this.sysTenantMapper.selectDataPage(new Page<>(), params);
        List<SysTenantPageVO> list = result.getRecords();
        list.forEach(SysTenantPageVO::handleData);
        return result;
    }

    @Override
    public List<SysTenantListVO> list(SysTenantListDTO params) {
        return this.sysTenantMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysTenantSaveDTO params) {
        Integer id = params.getId();
        boolean isAdd = id == null;
        String name = params.getName();
        Integer customTenantId = params.getCustomTenantId();

        // 校验名称是否重复
        SysTenant sysTenantOld = this.sysTenantMapper.selectOne(new LambdaQueryWrapper<SysTenant>().eq(SysTenant::getName, name).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysTenantOld == null || sysTenantOld.getId().equals(id), "名称重复，请重新输入！");

        Integer packageId = params.getPackageId();
        SysTenant sysTenant = SysTenant.builder()
                .id(id)
                .name(name)
                .adminName(params.getAdminName())
                .adminPhone(params.getAdminPhone())
                .status(params.getStatus())
                .expireTime(params.getExpireTime())
                .accountCount(params.getAccountCount())
                .packageId(packageId)
                .sort(params.getSort())
                .build();

        if (isAdd && customTenantId != null) {
            sysTenant.setId(customTenantId);
            // 看下自定义租户id有没有重复的
            Assert.isNull(this.sysTenantMapper.selectById(customTenantId), "租户ID重复，请重新输入！");
        }

        sysTenant.insertOrUpdate();
        id = sysTenant.getId();

        if (isAdd) {
            // 同步系统租户下的部分角色信息
            List<SysRoleBaseVO> roleList = TenantUtil.executeByTenantId(AppConstant.SMALL_BOOT_TENANT_ID, () -> this.iSysRoleService.list(SysRoleBaseDTO.builder()
                    .isRefreshAllTenant(true)
                    .excludeRoleIdList(
                            iSysRoleService.getRoleIdByCodes(Lists.newArrayList(
                                    SysRoleCodeEnum.超级管理员,
                                    SysRoleCodeEnum.系统管理员,
                                    SysRoleCodeEnum.租户管理员
                            ))
                    )
                    .build()));

            // 创建租户下的用户 & 分配角色权限
            TenantUtil.executeRemoveFlag(() -> {
                TenantIdContext.setTenantId(sysTenant.getId());
                SysTenantPackage sysTenantPackage = this.iSysTenantPackageService.detail(packageId);

                // 创建角色
                Integer roleId = this.iSysRoleService.addOrUpdateData(
                        SysRoleSaveDTO.builder()
                                .parentId(AppConstant.PARENT_ID)
                                .name(SysRoleCodeEnum.租户管理员.getName())
                                .code(SysRoleCodeEnum.租户管理员.getCode())
                                .isFixed(true)
                                .isRefreshAllTenant(true)
                                .sort(SysRoleCodeEnum.租户管理员.getSort())
                                .build()
                );

                // 给角色绑定权限
                this.iSysPermBusinessService.saveRoleRePerm(
                        SysRoleRePermSaveDTO.builder()
                                .roleId(roleId)
                                .menuIdList(sysTenantPackage.getMenuIdList())
                                .build()
                );

                // 创建用户
                Integer userId = this.iSysUserService.addOrUpdateData(SysUserSaveDTO.builder()
                        .username(params.getUsername())
                        .password(params.getPassword())
                        .nickname(params.getAdminName())
                        .phone(params.getAdminPhone())
                        .roleIdList(Lists.newArrayList(roleId))
                        .isUpdateRolePerm(true)
                        .isFixed(true)
                        .build());
                sysTenant.setAdminUserId(userId);
                sysTenant.updateById();

                // 同步系统租户下的同步更新角色数据（排除租户管理员）
                roleList.forEach(sysRoleItem -> {
                    Integer sysRoleIdItem = sysRoleItem.getRoleId();
                    // 创建角色
                    Integer roleIdItem = this.iSysRoleService.addOrUpdateData(
                            SysRoleSaveDTO.builder()
                                    .parentId(AppConstant.PARENT_ID)
                                    .name(sysRoleItem.getName())
                                    .code(sysRoleItem.getCode())
                                    .isFixed(sysRoleItem.getIsFixed())
                                    .isRefreshAllTenant(sysRoleItem.getIsRefreshAllTenant())
                                    .sort(sysRoleItem.getSort())
                                    .build()
                    );
                    // 给角色绑定权限
                    this.iSysPermBusinessService.saveRoleRePerm(
                            SysRoleRePermSaveDTO.builder()
                                    .roleId(roleIdItem)
                                    .menuIdList(TenantUtil.executeByTenantId(AppConstant.SMALL_BOOT_TENANT_ID, () -> this.iSysRoleMenuService.getMenuIdsByRoleId(sysRoleIdItem)))
                                    .scopeIdList(TenantUtil.executeByTenantId(AppConstant.SMALL_BOOT_TENANT_ID, () -> this.iSysRoleScopeService.getScopeIdListByRoleId(sysRoleIdItem)))
                                    .build()
                    );
                });
            });
        } else {
            this.refreshTenantRePerm(sysTenant.getId());
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.sysTenantMapper.deleteById(id);
        //  删除整个租户下的所有数据...  tips: 真实业务最好使用逻辑删除
        String dbName = this.systemProperty.getMysql().getMaster().getDbName();
        TenantUtil.executeRemoveFlag(() -> {
            List<String> sqlList = this.sysTenantMapper.selectPhysicsDelAllDataSql(dbName, id);
            sqlList.forEach(this.sysTenantMapper::execSql);
        });
    }

    /**
     * 刷新租户权限
     */
    private void refreshTenantRePerm(Integer tenantId) {
        if (tenantId == null) {
            return;
        }
        TenantUtil.executeRemoveFlag(() -> this.iSysPermBusinessService.refreshTenantRePerm(tenantId));
    }

    @Override
    public SysTenant checkData(Integer tenantId) {
        SysTenant sysTenant = this.detail(tenantId);
        if (AppConstant.SMALL_BOOT_TENANT_ID.equals(tenantId)) {
            return sysTenant;
        }
        Assert.isTrue(Objects.equals(CommonStatusEnum.ENABLE.getStatus(), sysTenant.getStatus()), "服务已停用！");
        Date expireTime = sysTenant.getExpireTime();
        if (expireTime != null) {
            Assert.isTrue(expireTime.after(new Date()), "限制：租户服务已到期！\n过期时间：" + MyDateUtil.dateToStr(expireTime));
        }
        return sysTenant;
    }
}
