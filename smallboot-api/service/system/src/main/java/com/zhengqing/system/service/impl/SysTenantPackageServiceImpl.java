package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.enums.YesNoEnum;
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
import org.apache.commons.compress.utils.Lists;
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
        List<Integer> menuIdList = params.getMenuIdList();
        List<Integer> permissionIdList = params.getPermissionIdList();
        Integer status = params.getStatus();

        // 1、保存套餐
        SysTenantPackage sysTenantPackage = SysTenantPackage.builder()
                .id(params.getId())
                .name(params.getName())
                .status(status)
                .menuIdList(menuIdList)
                .permissionIdList(permissionIdList)
                .remark(params.getRemark())
                .build();
        sysTenantPackage.insertOrUpdate();

        // 2、更新权限
        Integer oldTenantId = TenantIdContext.getTenantId();
        // 查询该套餐关联的租户ID
        List<SysTenantListVO> tenantList = this.iSysTenantService.list(SysTenantListDTO.builder().packageId(sysTenantPackage.getId()).build());
        if (YesNoEnum.NO.getValue().equals(status)) {
            menuIdList = Lists.newArrayList();
            permissionIdList = Lists.newArrayList();
        }
        for (SysTenantListVO e : tenantList) {
            this.iSysPermBusinessService.refreshTenantRePerm(e.getId(), menuIdList, permissionIdList);
        }
        TenantIdContext.setTenantId(oldTenantId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.sysTenantPackageMapper.deleteById(id);
    }

}
