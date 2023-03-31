package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.system.entity.SysRolePermission;
import com.zhengqing.system.mapper.SysRolePermissionMapper;
import com.zhengqing.system.model.bo.SysRoleRePermBO;
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
    public void savePerm(Integer roleId, List<Integer> permissionIdList) {
        if (CollectionUtils.isEmpty(permissionIdList)) {
            return;
        }
        List<SysRolePermission> saveList = Lists.newArrayList();
        permissionIdList.forEach(btnId ->
                saveList.add(SysRolePermission.builder()
                        .roleId(roleId)
                        .permissionId(btnId)
                        .build())
        );
        this.saveBatch(saveList);
    }
}
