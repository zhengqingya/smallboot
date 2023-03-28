package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysRoleMenu;
import com.zhengqing.system.mapper.SysRoleMenuMapper;
import com.zhengqing.system.model.dto.SysRoleMenuBtnSaveDTO;
import com.zhengqing.system.model.dto.SysRoleMenuSaveDTO;
import com.zhengqing.system.model.dto.SysRolePermissionSaveDTO;
import com.zhengqing.system.model.vo.SysMenuTreeVO;
import com.zhengqing.system.service.ISysRoleMenuService;
import com.zhengqing.system.service.ISysRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
@Transactional(rollbackFor = Exception.class)
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private ISysRolePermissionService sysRolePermissionService;

    @Override
    public List<Integer> getMenuIdsByRoleId(Integer roleId) {
        return this.sysRoleMenuMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public List<Integer> getMenuIdsByRoleIds(List<Integer> roleIdList) {
        return this.sysRoleMenuMapper.selectMenuIdsByRoleIds(roleIdList);
    }

    @Override
    public void saveRoleMenuIds(SysRoleMenuSaveDTO params) {
        Integer roleId = params.getRoleId();

        // 1、先删除角色关联的菜单权限信息
        this.sysRoleMenuMapper.deleteAllMenusByRoleId(roleId);

        // 2、再保存角色关联的菜单权限信息
        List<Integer> menuIdList = params.getMenuIdList();
        if (!CollectionUtils.isEmpty(menuIdList)) {
            List<SysRoleMenu> roleMenuList = Lists.newArrayList();
            menuIdList.forEach(menuId ->
                    roleMenuList.add(SysRoleMenu.builder()
                            .roleId(roleId)
                            .menuId(menuId)
                            .build())
            );
            this.saveBatch(roleMenuList);
        }
    }

    @Override
    public void saveRolePermission(SysRolePermissionSaveDTO params) {
        Integer roleId = params.getRoleId();

        // 1、先保存角色关联的菜单权限
        this.saveRoleMenuIds(
                SysRoleMenuSaveDTO.builder()
                        .roleId(roleId)
                        .menuIdList(params.getMenuIdList())
                        .build()
        );

        // 2、再保存角色关联的按钮权限
        this.handleMenuAndBtnPermissionTree(roleId, params.getMenuAndBtnPermissionTree());
    }

    /**
     * 递归处理菜单+按钮权限树信息数据 -> 保存按钮权限数据
     *
     * @param roleId                   角色id
     * @param menuAndBtnPermissionTree 权限树信息
     * @return void
     * @author zhengqingya
     * @date 2020/9/14 11:24
     */
    public void handleMenuAndBtnPermissionTree(Integer roleId, List<SysMenuTreeVO> menuAndBtnPermissionTree) {
        if (CollectionUtils.isEmpty(menuAndBtnPermissionTree)) {
            return;
        }
        menuAndBtnPermissionTree.forEach(menu -> {
            Integer menuId = menu.getMenuId();
            // 1、先删除按钮权限数据
            this.sysRolePermissionService.deleteBtnsByRoleIdAndMenuId(roleId, menuId);

            // 2、保存按钮权限数据
            List<Integer> permissionIdList = menu.getPermissionIdList();
            if (!CollectionUtils.isEmpty(permissionIdList)) {
                SysRoleMenuBtnSaveDTO btnSaveItem = new SysRoleMenuBtnSaveDTO();
                btnSaveItem.setRoleId(roleId);
                btnSaveItem.setMenuId(menuId);
                btnSaveItem.setPermissionIdList(permissionIdList);
                this.sysRolePermissionService.saveRoleReMenuBtnIds(btnSaveItem);
            }

            // 3、判断如果有子树则递归下去
            List<SysMenuTreeVO> children = menu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                this.handleMenuAndBtnPermissionTree(roleId, children);
            }
        });
    }

    @Override
    public void deleteAllMenusByRoleId(Integer roleId) {
        this.sysRoleMenuMapper.deleteAllMenusByRoleId(roleId);
    }

}
