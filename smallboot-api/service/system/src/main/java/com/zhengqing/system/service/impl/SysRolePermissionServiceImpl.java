package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.system.entity.SysRolePermission;
import com.zhengqing.system.mapper.SysRolePermissionMapper;
import com.zhengqing.system.model.bo.SysRoleRePermBO;
import com.zhengqing.system.model.dto.SysRoleRePermIdsSaveDTO;
import com.zhengqing.system.model.vo.SysRoleMenuBtnListVO;
import com.zhengqing.system.service.ISysRolePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理-角色关联权限 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    private final SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRoleMenuBtnListVO> listRoleReMenuBtn() {
        return this.sysRolePermissionMapper.selectDataListRoleReMenuBtn();
    }

    @Override
    public List<Integer> getPermByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        return this.sysRolePermissionMapper.selectIdsByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    public Map<Integer, List<Integer>> mapRoleRePerm() {
        List<SysRoleRePermBO> list = this.sysRolePermissionMapper.selectRoleRePerm();

        Map<Integer, List<Integer>> map = Maps.newHashMap();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }
        for (SysRoleRePermBO item : list) {
            map.computeIfAbsent(item.getRoleId(), k -> new LinkedList<>()).add(item.getPermissionId());
        }
        return map;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delByRoleId(Integer roleId) {
        this.sysRolePermissionMapper.delByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        this.sysRolePermissionMapper.delByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePerm(SysRoleRePermIdsSaveDTO params) {
        Integer roleId = params.getRoleId();
        List<Integer> permissionIdList = params.getPermissionIdList();

        if (CollUtil.isEmpty(permissionIdList)) {
            // 直接根据角色id删除关联所有按钮权限
            this.delByRoleId(roleId);
            return;
        }

        // 1、查询角色关联的旧权限信息
        List<SysRolePermission> rePermListOld = this.sysRolePermissionMapper.selectList(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, roleId));
        // 权限id -> 主键id
        Map<Integer, Integer> permReIdMapOld = rePermListOld.stream().collect(Collectors.toMap(SysRolePermission::getPermissionId, SysRolePermission::getId, (oldData, newData) -> newData));
        // 旧权限id
        List<Integer> rePermIdListOld = rePermListOld.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());

        // 2、筛选出需删除的旧数据
        List<Integer> removePermIdList = rePermIdListOld.stream().filter(oldPermId -> !permissionIdList.contains(oldPermId)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(removePermIdList)) {
            // 删除角色关联的权限信息
            this.sysRolePermissionMapper.delete(
                    new LambdaQueryWrapper<SysRolePermission>()
                            .eq(SysRolePermission::getRoleId, roleId)
                            .in(SysRolePermission::getPermissionId, removePermIdList)
            );
        }

        // 3、再保存角色关联的权限信息
        List<SysRolePermission> saveList = Lists.newArrayList();
        permissionIdList.forEach(btnId ->
                saveList.add(SysRolePermission.builder()
                        .id(permReIdMapOld.get(btnId))
                        .roleId(roleId)
                        .permissionId(btnId)
                        .build())
        );
        this.saveOrUpdateBatch(saveList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshTenantRePerm(Integer tenantId, List<Integer> permissionIdList) {
        TenantIdContext.setTenantId(tenantId);

        // 查询旧权限信息
        List<SysRolePermission> oldList = this.sysRolePermissionMapper.selectList(null);
        // 旧权限id
        List<Integer> oldIdList = oldList.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        // 拿到要删除的旧权限id
        List<Integer> dlePermIdList = CollUtil.subtractToList(oldIdList, permissionIdList);
        if (CollUtil.isNotEmpty(dlePermIdList)) {
            this.sysRolePermissionMapper.delete(new LambdaQueryWrapper<SysRolePermission>().in(SysRolePermission::getPermissionId, dlePermIdList));
        }

        TenantIdContext.remove();
    }

}
