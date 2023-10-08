package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysTenantPackage;
import com.zhengqing.system.mapper.SysTenantPackageMapper;
import com.zhengqing.system.model.dto.SysTenantPackageListDTO;
import com.zhengqing.system.model.dto.SysTenantPackagePageDTO;
import com.zhengqing.system.model.dto.SysTenantPackageSaveDTO;
import com.zhengqing.system.model.vo.SysTenantPackageListVO;
import com.zhengqing.system.model.vo.SysTenantPackagePageVO;
import com.zhengqing.system.service.ISysTenantPackageService;
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
        SysTenantPackage.builder()
                .id(params.getId())
                .name(params.getName())
                .status(params.getStatus())
                .menuIdList(params.getMenuIdList())
                .permissionIdList(params.getPermissionIdList())
                .remark(params.getRemark())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.sysTenantPackageMapper.deleteById(id);
    }

}
