package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.db.util.TenantUtil;
import com.zhengqing.system.entity.SysTenant;
import com.zhengqing.system.entity.SysTenantPackage;
import com.zhengqing.system.mapper.SysTenantPackageMapper;
import com.zhengqing.system.model.dto.SysTenantListDTO;
import com.zhengqing.system.model.dto.SysTenantPackageListDTO;
import com.zhengqing.system.model.dto.SysTenantPackagePageDTO;
import com.zhengqing.system.model.dto.SysTenantPackageSaveDTO;
import com.zhengqing.system.model.vo.SysTenantListVO;
import com.zhengqing.system.model.vo.SysTenantPackageListVO;
import com.zhengqing.system.model.vo.SysTenantPackagePageVO;
import com.zhengqing.system.service.ISysPermBusinessService;
import com.zhengqing.system.service.ISysTenantPackageService;
import com.zhengqing.system.service.ISysTenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 系统管理-租户套餐 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 10:34
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysTenantPackageServiceImpl extends ServiceImpl<SysTenantPackageMapper, SysTenantPackage> implements ISysTenantPackageService {

    private final SysTenantPackageMapper sysTenantPackageMapper;
    @Lazy
    @Resource
    private ISysTenantService iSysTenantService;
    @Lazy
    @Resource
    private ISysPermBusinessService iSysPermBusinessService;

    @Override
    public SysTenantPackage detail(Integer id) {
        SysTenantPackage sysTenantPackage = this.sysTenantPackageMapper.selectById(id);
        Assert.notNull(sysTenantPackage, "该套餐数据不存在！");
        return sysTenantPackage;
    }

    @Override
    public SysTenantPackage detailReTenantId(Integer tenantId) {
        SysTenant sysTenant = this.iSysTenantService.detail(tenantId);
        return this.detail(sysTenant.getPackageId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenantIdRePerm(Integer tenantId, List<Integer> menuIdList, List<Integer> permissionIdList) {
        // 1、查询租户套餐
        SysTenantPackage sysTenantPackage = this.detailReTenantId(tenantId);
        // 2、更新权限
        sysTenantPackage.setMenuIdList(menuIdList);
        sysTenantPackage.setPermissionIdList(permissionIdList);
        sysTenantPackage.updateById();
    }

    @Override
    public IPage<SysTenantPackagePageVO> page(SysTenantPackagePageDTO params) {
        return this.sysTenantPackageMapper.selectDataPage(new Page<>(), params);
    }

    @Override
    public List<SysTenantPackageListVO> list(SysTenantPackageListDTO params) {
        return this.sysTenantPackageMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysTenantPackageSaveDTO params) {
        Integer id = params.getId();
        Assert.isFalse(AppConstant.SMALL_BOOT_TENANT_ID_PACKAGE_ID.equals(id), "超级套餐无法操作！");
        boolean isAdd = id != null;
        List<Integer> menuIdList = params.getMenuIdList();
        List<Integer> permissionIdList = params.getPermissionIdList();

        // 1、保存套餐
        SysTenantPackage sysTenantPackage = SysTenantPackage.builder()
                .id(id)
                .name(params.getName())
                .status(params.getStatus())
                .menuIdList(menuIdList)
                .permissionIdList(permissionIdList)
                .remark(params.getRemark())
                .build();
        sysTenantPackage.insertOrUpdate();

        // 2、刷新租户关联权限
        this.refreshAllTenantPermByPackageId(sysTenantPackage.getId());
    }


    /**
     * 刷新套餐下关联的所有租户权限
     *
     * @param id 主键id
     * @return void
     * @author zhengqingya
     * @date 2023/10/8 21:23
     */
    private void refreshAllTenantPermByPackageId(Integer id) {
        // 1、查询该套餐关联的租户
        List<SysTenantListVO> tenantList = this.iSysTenantService.list(SysTenantListDTO.builder().packageId(id).build());
        if (CollUtil.isEmpty(tenantList)) {
            return;
        }

        // 2、更新权限
        TenantUtil.execute(() -> tenantList.forEach(e -> this.iSysPermBusinessService.refreshTenantRePerm(e.getId())));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        Assert.isFalse(AppConstant.SMALL_BOOT_TENANT_ID_PACKAGE_ID.equals(id), "超级套餐无法操作！");
        // 1、校验数据是否存在
        this.detail(id);
        // 2、刷新租户关联权限
        this.refreshAllTenantPermByPackageId(id);
        // 3、删除套餐
        this.sysTenantPackageMapper.deleteById(id);
    }

}
