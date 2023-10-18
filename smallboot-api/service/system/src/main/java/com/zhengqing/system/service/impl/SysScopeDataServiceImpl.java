package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysScopeData;
import com.zhengqing.system.mapper.SysScopeDataMapper;
import com.zhengqing.system.model.dto.SysScopeDataBaseDTO;
import com.zhengqing.system.model.dto.SysScopeDataSaveDTO;
import com.zhengqing.system.model.vo.SysScopeDataBaseVO;
import com.zhengqing.system.service.ISysRoleScopeService;
import com.zhengqing.system.service.ISysScopeDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 系统管理-数据权限 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysScopeDataServiceImpl extends ServiceImpl<SysScopeDataMapper, SysScopeData> implements ISysScopeDataService {

    private final SysScopeDataMapper sysScopeDataMapper;
    private final ISysRoleScopeService iSysRoleScopeService;

    @Override
    public IPage<SysScopeDataBaseVO> page(SysScopeDataBaseDTO params) {
        IPage<SysScopeDataBaseVO> result = this.sysScopeDataMapper.selectDataList(new Page<>(), params);
        List<SysScopeDataBaseVO> list = result.getRecords();
        list.forEach(SysScopeDataBaseVO::handleData);
        return result;
    }

    @Override
    public List<SysScopeDataBaseVO> list(SysScopeDataBaseDTO params) {
        return this.sysScopeDataMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysScopeDataSaveDTO params) {
        SysScopeData.builder()
                .id(params.getId())
                .menuId(params.getMenuId())
                .scopeName(params.getScopeName())
                .scopeColumn(params.getScopeColumn())
                .scopeVisibleField(params.getScopeVisibleField())
                .scopeClass(params.getScopeClass())
                .scopeType(params.getScopeType())
                .scopeValue(params.getScopeValue())
                .remark(params.getRemark())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.iSysRoleScopeService.delByScopeIdList(Lists.newArrayList(id));
        this.sysScopeDataMapper.deleteById(id);
    }

}
