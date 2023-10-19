package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.system.entity.SysScopeData;
import com.zhengqing.system.entity.SysTenantPackage;
import com.zhengqing.system.enums.SysMenuTypeEnum;
import com.zhengqing.system.mapper.SysScopeDataMapper;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysMenuTreeDTO;
import com.zhengqing.system.model.dto.SysScopeDataBaseDTO;
import com.zhengqing.system.model.dto.SysScopeDataSaveDTO;
import com.zhengqing.system.model.vo.SysScopeDataBaseVO;
import com.zhengqing.system.service.ISysMenuService;
import com.zhengqing.system.service.ISysRoleScopeService;
import com.zhengqing.system.service.ISysScopeDataService;
import com.zhengqing.system.service.ISysTenantPackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final ISysTenantPackageService iSysTenantPackageService;
    private final ISysMenuService iSysMenuService;

    /**
     * 查询租户关联权限
     */
    private List<Integer> getTenantReMenuIdList() {
        SysTenantPackage sysTenantPackage = this.iSysTenantPackageService.detailReTenantId(TenantIdContext.getTenantId());
        return sysTenantPackage.getMenuIdList();
    }

    @Override
    public IPage<SysScopeDataBaseVO> page(SysScopeDataBaseDTO params) {
        params.setTenantReMenuIdList(this.getTenantReMenuIdList());
        IPage<SysScopeDataBaseVO> result = this.sysScopeDataMapper.selectDataList(new Page<>(), params);
        List<SysScopeDataBaseVO> list = result.getRecords();
        list.forEach(SysScopeDataBaseVO::handleData);
        return result;
    }

    @Override
    public List<SysScopeDataBaseVO> list(SysScopeDataBaseDTO params) {
        params.setTenantReMenuIdList(this.getTenantReMenuIdList());
        return this.sysScopeDataMapper.selectDataList(params);
    }

    @Override
    public List<SysScopeDataBaseVO> tree(SysScopeDataBaseDTO params) {
        // 数据权限
        List<SysScopeDataBaseVO> scopeList = this.list(params);
        List<Integer> menuIdList = scopeList.stream().map(SysScopeDataBaseVO::getMenuId).distinct().sorted().collect(Collectors.toList());
        Map<Integer, List<SysScopeDataBaseVO>> scopeMap = scopeList.stream().collect(Collectors.groupingBy(SysScopeDataBaseVO::getMenuId, Collectors.mapping(t -> t, Collectors.toList())));

        // 菜单
        List<SysMenuTree> menuTree = this.iSysMenuService.tree(SysMenuTreeDTO.builder().childMenuIdList(menuIdList).type(SysMenuTypeEnum.菜单.getType()).build());

        // 组装树
        return this.recurveData(scopeMap, menuTree);
    }

    /**
     * 递归
     *
     * @param scopeMap 菜单id -> 数据权限
     * @param list     所有数据
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysScopeDataBaseVO> recurveData(Map<Integer, List<SysScopeDataBaseVO>> scopeMap, List<SysMenuTree> list) {
        List<SysScopeDataBaseVO> result = Lists.newArrayList();
        list.forEach(item -> {
            Integer menuId = item.getId();
            List<SysScopeDataBaseVO> childList = Lists.newArrayList();
            List<SysMenuTree> children = item.getChildren();
            if (CollUtil.isNotEmpty(children)) {
                childList = this.recurveData(scopeMap, children);
            }

            List<SysScopeDataBaseVO> scopeList = scopeMap.get(menuId);
            if (CollUtil.isNotEmpty(scopeList)) {
                childList.addAll(scopeList);
            }
            result.add(SysScopeDataBaseVO.builder()
                    .customId("m" + menuId)
                    .customName(item.getName())
                    .menuFullName(item.getFullName())
                    .children(childList)
                    .build());
        });
        return result;
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
