package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysRoleMenu;
import com.zhengqing.system.mapper.SysRoleMenuMapper;
import com.zhengqing.system.model.dto.SysRoleReMenuSaveDTO;
import com.zhengqing.system.service.ISysRoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 系统管理 - 角色菜单关联服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<Integer> getMenuIdsByRoleId(Integer roleId) {
        return this.sysRoleMenuMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public List<Integer> getMenuIdsByRoleIds(List<Integer> roleIdList) {
        return this.sysRoleMenuMapper.selectMenuIdsByRoleIds(roleIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenuIds(SysRoleReMenuSaveDTO params) {
        Integer roleId = params.getRoleId();

        // 1、先删除角色关联的菜单权限信息
        this.sysRoleMenuMapper.deleteAllMenusByRoleId(roleId);

        // 2、再保存角色关联的菜单权限信息
        List<Integer> menuIdList = params.getMenuIdList();
        if (CollectionUtils.isEmpty(menuIdList)) {
            return;
        }

        List<SysRoleMenu> roleMenuList = Lists.newArrayList();
        menuIdList.forEach(menuId ->
                roleMenuList.add(SysRoleMenu.builder()
                        .roleId(roleId)
                        .menuId(menuId)
                        .build())
        );
        this.saveBatch(roleMenuList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllMenusByRoleId(Integer roleId) {
        this.sysRoleMenuMapper.deleteAllMenusByRoleId(roleId);
    }

}
