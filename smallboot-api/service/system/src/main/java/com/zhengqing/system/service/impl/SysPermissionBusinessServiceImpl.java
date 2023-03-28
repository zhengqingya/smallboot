package com.zhengqing.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.constant.SecurityConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.model.dto.SysRolePermissionSaveDTO;
import com.zhengqing.system.model.vo.SysMenuTreeVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
public class SysPermissionBusinessServiceImpl implements ISysPermissionBusinessService {

    private final ISysPermissionService sysPermissionService;
    private final ISysMenuService menuService;
    private final ISysRoleService sysRoleService;
    private final ISysRoleMenuService sysRoleMenuService;
    private final ISysRolePermissionService sysRolePermissionService;

    @Override
    public void initSuperAdminPerm() {
        // 1、先查询所有菜单和按钮数据
        List<SysMenuTreeVO> menuTree = this.menuService.tree();
        Integer roleId = this.sysRoleService.getRoleIdForSuperAdmin();
        List<Integer> menuIdList = this.menuService.list().stream().map(SysMenu::getMenuId).collect(Collectors.toList());

        // 2、保存角色关联的菜单和按钮权限
        this.sysRoleMenuService.saveRolePermission(SysRolePermissionSaveDTO.builder()
                .roleId(roleId)
                .menuIdList(menuIdList)
                .menuAndBtnPermissionTree(menuTree)
                .build());
        this.sysRolePermissionService.savePerm(roleId, this.sysPermissionService.listPermissionId());
        log.info("初始化超级管理员权限成功!");
    }

    @Override
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

}
