package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.model.bo.ScopeDataBO;
import com.zhengqing.system.entity.SysRoleScope;
import com.zhengqing.system.mapper.SysRoleScopeMapper;
import com.zhengqing.system.model.dto.SysRoleReScopeSaveDTO;
import com.zhengqing.system.model.dto.SysRoleScopeListDTO;
import com.zhengqing.system.model.vo.SysRoleScopeListVO;
import com.zhengqing.system.service.ISysRoleScopeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 系统管理-角色&数据权限关联表 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleScopeServiceImpl extends ServiceImpl<SysRoleScopeMapper, SysRoleScope> implements ISysRoleScopeService {

    private final SysRoleScopeMapper sysRoleScopeMapper;

    @Override
    public List<SysRoleScopeListVO> list(SysRoleScopeListDTO params) {
        return this.sysRoleScopeMapper.selectDataList(params);
    }

    @Override
    public List<Integer> getScopeIdListByRoleId(Integer roleId) {
        List<SysRoleScopeListVO> list = this.list(SysRoleScopeListDTO.builder().roleId(roleId).build());
        return list.stream().map(SysRoleScopeListVO::getScopeId).collect(Collectors.toList());
    }

    @Override
    public List<ScopeDataBO> getScopeListReRoleIdList(List<Integer> roleIdList) {
        return this.sysRoleScopeMapper.selectScopeListReRoleIdList(roleIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveScopeData(SysRoleReScopeSaveDTO params) {
        Integer roleId = params.getRoleId();
        List<Integer> scopeIdList = params.getScopeIdList();

        if (CollUtil.isEmpty(scopeIdList)) {
            // 直接删除角色关联的所有数据权限
            this.delByRoleId(roleId);
            return;
        }

        // 1、查询角色关联的旧菜单权限信息
        List<SysRoleScope> roleReDataListOld = this.sysRoleScopeMapper.selectList(new LambdaQueryWrapper<SysRoleScope>().eq(SysRoleScope::getRoleId, roleId));
        // 权限id -> 主键id
        Map<Integer, Integer> mapOld = roleReDataListOld.stream().collect(Collectors.toMap(SysRoleScope::getScopeId, SysRoleScope::getId, (oldData, newData) -> newData));
        // 旧权限id
        List<Integer> roleReScopeIdListOld = roleReDataListOld.stream().map(SysRoleScope::getScopeId).collect(Collectors.toList());

        // 2、拿到要删除的旧权限id
        List<Integer> delScopeIdList = CollUtil.subtractToList(roleReScopeIdListOld, scopeIdList);
        if (CollUtil.isNotEmpty(delScopeIdList)) {
            // 删除角色关联的权限信息
            this.delByScopeIdList(delScopeIdList);
        }

        // 3、再保存角色关联的权限信息
        List<SysRoleScope> roleScopeList = Lists.newArrayList();
        scopeIdList.forEach(scopeId -> {
            if (mapOld.get(scopeId) == null) {
                roleScopeList.add(SysRoleScope.builder()
                        .tenantId(TenantIdContext.getTenantId())
                        .id(null)
                        .roleId(roleId)
                        .scopeId(scopeId)
                        .build());
            }
        });
        if (CollUtil.isNotEmpty(roleScopeList)) {
            this.sysRoleScopeMapper.insertBatchSomeColumn(roleScopeList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delByRoleId(Integer roleId) {
        this.sysRoleScopeMapper.delete(new LambdaQueryWrapper<SysRoleScope>().eq(SysRoleScope::getRoleId, roleId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delReMenuIdList(List<Integer> delMenuIdList) {
        if (CollUtil.isEmpty(delMenuIdList)) {
            return;
        }
        this.sysRoleScopeMapper.delReMenuIdList(delMenuIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delByScopeIdList(List<Integer> scopeIdList) {
        this.sysRoleScopeMapper.delete(new LambdaQueryWrapper<SysRoleScope>().in(SysRoleScope::getScopeId, scopeIdList));
    }


}
