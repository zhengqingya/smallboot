package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.system.entity.SysTenant;
import com.zhengqing.system.mapper.SysTenantMapper;
import com.zhengqing.system.model.dto.SysTenantListDTO;
import com.zhengqing.system.model.dto.SysTenantPageDTO;
import com.zhengqing.system.model.dto.SysTenantSaveDTO;
import com.zhengqing.system.model.vo.SysTenantListVO;
import com.zhengqing.system.model.vo.SysTenantPageVO;
import com.zhengqing.system.service.ISysPermBusinessService;
import com.zhengqing.system.service.ISysTenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ISysPermBusinessService iSysPermBusinessService;

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
        Assert.isFalse(AppConstant.TENANT_ID_SMALL_BOOT.equals(id), "系统租户不支持操作！");
        SysTenant.builder()
                .id(id)
                .name(params.getName())
                .contactName(params.getContactName())
                .contactPhone(params.getContactPhone())
                .status(params.getStatus())
                .expireTime(params.getExpireTime())
                .accountCount(params.getAccountCount())
                .packageId(params.getPackageId())
                .build()
                .insertOrUpdate();

        if (id != null) {
            this.iSysPermBusinessService.refreshTenantRePerm(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        Assert.isFalse(AppConstant.TENANT_ID_SMALL_BOOT.equals(id), "系统租户不支持操作！");
        this.sysTenantMapper.deleteById(id);
        this.iSysPermBusinessService.refreshTenantRePerm(id);
    }

}
