package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ISysTenantService iSysTenantService;
    private final ISysPermBusinessService iSysPermBusinessService;

    @Override
    public SysTenantPackage detail(Integer id) {
        SysTenantPackage sysTenantPackage = this.sysTenantPackageMapper.selectById(id);
        Assert.notNull(sysTenantPackage, "该套餐数据不存在！");
        return sysTenantPackage;
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

        if (isAdd) {
            return;
        }

        // 2、删除租户关联权限
        this.delTenantRePerm(sysTenantPackage.getId(), menuIdList, permissionIdList);
    }


    /**
     * 删除租户关联权限
     *
     * @param id            主键id
     * @param newMenuIdList 新菜单
     * @param newPermIdList 新按钮
     * @return void
     * @author zhengqingya
     * @date 2023/10/8 21:23
     */
    private void delTenantRePerm(Integer id, List<Integer> newMenuIdList, List<Integer> newPermIdList) {
        // 1、查询该套餐关联的租户
        List<SysTenantListVO> tenantList = this.iSysTenantService.list(SysTenantListDTO.builder().packageId(id).build());
        if (CollUtil.isEmpty(tenantList)) {
            return;
        }

        // 2、查询旧权限
        SysTenantPackage sysTenantPackageOld = this.detail(id);
        // 拿到要删除的旧权限id
        List<Integer> dleMenuIdList = CollUtil.subtractToList(sysTenantPackageOld.getMenuIdList(), newMenuIdList);
        List<Integer> dlePermIdList = CollUtil.subtractToList(sysTenantPackageOld.getPermissionIdList(), newPermIdList);

        // 3、更新权限
        Integer oldTenantId = TenantIdContext.getTenantId();
        tenantList.forEach(e -> this.iSysPermBusinessService.delTenantRePerm(e.getId(), dleMenuIdList, dlePermIdList));
        TenantIdContext.setTenantId(oldTenantId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        // 1、校验数据是否存在
        this.detail(id);
        // 2、删除租户关联权限
        this.delTenantRePerm(id, Lists.newArrayList(), Lists.newArrayList());
        // 3、删除套餐
        this.sysTenantPackageMapper.deleteById(id);
    }

}
