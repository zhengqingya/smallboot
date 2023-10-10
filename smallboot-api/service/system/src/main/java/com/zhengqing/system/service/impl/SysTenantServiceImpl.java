package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.db.util.TenantUtil;
import com.zhengqing.system.entity.SysTenant;
import com.zhengqing.system.entity.SysTenantPackage;
import com.zhengqing.system.enums.SysRoleCodeEnum;
import com.zhengqing.system.mapper.SysTenantMapper;
import com.zhengqing.system.model.bo.SysRoleRePermSaveBO;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysTenantListVO;
import com.zhengqing.system.model.vo.SysTenantPageVO;
import com.zhengqing.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    private final ISysUserRoleService iSysUserRoleService;

    @Override
    public SysTenant detail(Integer id) {
        SysTenant sysTenant = this.sysTenantMapper.selectById(id);
        Assert.notNull(sysTenant, "该租户不存在");
        return sysTenant;
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
        Assert.isFalse(AppConstant.SMALL_BOOT_TENANT_ID.equals(id), "系统租户不支持操作！");
        boolean isAdd = id == null;
        String name = params.getName();

        // 校验名称是否重复
        SysTenant sysTenantOld = this.sysTenantMapper.selectOne(new LambdaQueryWrapper<SysTenant>().eq(SysTenant::getName, name).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysTenantOld == null || sysTenantOld.getId().equals(id), "名称重复，请重新输入！");

        Integer packageId = params.getPackageId();
        SysTenant sysTenant = SysTenant.builder()
                .id(id)
                .name(name)
                .contactName(params.getContactName())
                .contactPhone(params.getContactPhone())
                .status(params.getStatus())
                .expireTime(params.getExpireTime())
                .accountCount(params.getAccountCount())
                .packageId(packageId)
                .build();
        sysTenant.insertOrUpdate();

        if (isAdd) {
            // 创建租户下的用户 & 分配角色权限
            TenantUtil.execute(() -> {
                TenantIdContext.setTenantId(sysTenant.getId());
                SysTenantPackage sysTenantPackage = this.iSysTenantPackageService.detail(packageId);

                // 创建角色
                Integer roleId = this.iSysRoleService.addOrUpdateData(
                        SysRoleSaveDTO.builder()
                                .name(SysRoleCodeEnum.租户管理员.getName())
                                .code(SysRoleCodeEnum.租户管理员.getCode())
                                .isFixed(true)
                                .build()
                );

                // 给角色绑定权限
                this.iSysPermBusinessService.saveRoleRePerm(
                        SysRoleRePermSaveBO.builder()
                                .roleId(roleId)
                                .menuIdList(sysTenantPackage.getMenuIdList())
                                .permissionIdList(sysTenantPackage.getPermissionIdList())
                                .build()
                );

                // 创建用户
                Integer userId = this.iSysUserService.addOrUpdateData(SysUserSaveDTO.builder()
                        .username(params.getUsername())
                        .nickname(params.getUsername())
                        .password(params.getPassword())
                        .phone(params.getContactPhone())
                        .build());

                // 绑定角色信息
                this.iSysUserRoleService.addOrUpdateData(
                        SysUserRoleSaveDTO.builder()
                                .userId(userId)
                                .roleIdList(Lists.newArrayList(roleId))
                                .build()
                );
            });
        } else {
            this.refreshTenantRePerm(sysTenant.getId());
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        Assert.isFalse(AppConstant.SMALL_BOOT_TENANT_ID.equals(id), "系统租户不支持操作！");
        this.refreshTenantRePerm(id);
        this.sysTenantMapper.deleteById(id);
    }

    /**
     * 刷新租户权限
     */
    private void refreshTenantRePerm(Integer tenantId) {
        if (tenantId == null) {
            return;
        }
        TenantUtil.execute(() -> this.iSysPermBusinessService.refreshTenantRePerm(tenantId));
    }

}
