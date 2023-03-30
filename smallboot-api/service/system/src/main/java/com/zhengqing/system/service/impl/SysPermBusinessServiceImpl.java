package com.zhengqing.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.constant.SecurityConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysRoleReMenuSaveDTO;
import com.zhengqing.system.model.dto.SysRoleRePermIdsSaveDTO;
import com.zhengqing.system.model.dto.SysRoleRePermSaveDTO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermVO;
import com.zhengqing.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理 - 权限系列缓存 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysPermBusinessServiceImpl implements ISysPermBusinessService {

    private final ISysPermissionService sysPermissionService;
    private final ISysMenuService menuService;
    private final ISysRoleService sysRoleService;
    private final ISysRoleMenuService sysRoleMenuService;
    private final ISysRolePermissionService sysRolePermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initSuperAdminPerm() {
        // 1、先查询所有菜单和按钮数据
        List<SysMenuTree> menuTree = this.menuService.tree(null);
        Integer roleId = this.sysRoleService.getRoleIdForSuperAdmin();
        List<Integer> menuIdList = this.menuService.list().stream().map(SysMenu::getMenuId).collect(Collectors.toList());

        // 2、保存角色关联的菜单和按钮权限
        this.saveRoleRePerm(
                SysRoleRePermSaveDTO.builder()
                        .roleId(roleId)
                        .menuIdList(menuIdList)
                        .menuTree(menuTree)
                        .build()
        );
        this.sysRolePermissionService.savePerm(roleId, this.sysPermissionService.listPermissionId());
        log.info("初始化超级管理员权限成功!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshRedisPerm() {
        // 1、先删除缓存
        RedisUtil.delete(SecurityConstant.URL_PERM_RE_ROLES);

        // 2、查询角色关联权限数据
        List<SysRoleRePermListVO> roleRePermList = this.sysPermissionService.listRoleRePerm();
        if (CollectionUtils.isEmpty(roleRePermList)) {
            return;
        }
        Map<String, String> roleReUrlPermMap = Maps.newHashMap();
        roleRePermList.forEach(item -> roleReUrlPermMap.put(item.getUrlPerm(), JSON.toJSONString(item.getRoleCodeList())));

        // 3、加入缓存中
        RedisUtil.hPutAll(SecurityConstant.URL_PERM_RE_ROLES, roleReUrlPermMap);
        log.info("初始化URL权限缓存成功!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleRePermIds(SysRoleRePermIdsSaveDTO params) {
        Integer roleId = params.getRoleId();
        Integer menuId = params.getMenuId();
        List<Integer> permissionIdList = params.getPermissionIdList();

        // 1、先删除
        this.sysRolePermissionService.deletePermByRoleIdAndMenuId(roleId, menuId);

        if (CollectionUtils.isEmpty(permissionIdList)) {
            return;
        }

        // 2、再保存
        this.sysRolePermissionService.savePerm(roleId, permissionIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleRePerm(SysRoleRePermSaveDTO params) {
        Integer roleId = params.getRoleId();

        // 1、先保存角色关联的菜单权限
        this.sysRoleMenuService.saveRoleMenuIds(
                SysRoleReMenuSaveDTO.builder()
                        .roleId(roleId)
                        .menuIdList(params.getMenuIdList())
                        .build()
        );


        // 3、再保存角色关联的按钮权限
        this.handleRoleRePermIds(roleId, params.getMenuTree());
    }

    /**
     * 保存角色关联的按钮权限数据
     *
     * @param roleId         角色id
     * @param menuRePermTree 权限树信息
     * @return void
     * @author zhengqingya
     * @date 2020/9/14 11:24
     */
    public void handleRoleRePermIds(Integer roleId, List<SysMenuTree> menuRePermTree) {
        if (CollectionUtils.isEmpty(menuRePermTree)) {
            return;
        }
        menuRePermTree.forEach(menu -> {
            Integer menuId = menu.getMenuId();

            // 保存按钮权限数据
            List<Integer> permissionIdList = menu.getPermList().stream().filter(SysRoleRePermVO::getIsHasPerm).map(SysRoleRePermVO::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(permissionIdList)) {
                this.saveRoleRePermIds(
                        SysRoleRePermIdsSaveDTO.builder()
                                .roleId(roleId)
                                .menuId(menuId)
                                .permissionIdList(permissionIdList)
                                .build()
                );
            }

            // 如果有子树则递归下去
            List<SysMenuTree> children = menu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                this.handleRoleRePermIds(roleId, children);
            }
        });
    }

}
