package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.constant.SecurityConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysRoleAllPermissionDetailVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
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

    private final ISysPermissionService iSysPermissionService;
    private final ISysUserService iSysUserService;
    private final ISysMenuService iSysMenuService;
    private final ISysRoleService iSysRoleService;
    private final ISysRoleMenuService iSysRoleMenuService;
    private final ISysUserRoleService iSysUserRoleService;
    private final ISysRolePermissionService iSysRolePermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initSuperAdminPerm() {
        // 1、先查询超级管理员角色id再绑定
        Integer roleId = this.iSysRoleService.getRoleIdForSuperAdmin();
        Assert.notNull(roleId, "超级管理员角色丢失！");
        this.iSysUserRoleService.addOrUpdateData(
                SysUserRoleSaveDTO.builder()
                        .userId(AppConstant.SYSTEM_SUPER_ADMIN_USER_ID)
                        .roleIdList(Lists.newArrayList(roleId))
                        .build()
        );

        // 2、先查询所有菜单和按钮数据
        List<SysMenuTree> menuTree = this.tree(Lists.newArrayList(), false);

        // 3、保存角色关联的菜单和按钮权限
        SysRoleRePermSaveDTO roleRePerm = SysRoleRePermSaveDTO.builder()
                .roleId(roleId)
                .menuTree(menuTree)
                .build();
        roleRePerm.handleParam();
        this.saveRoleRePerm(roleRePerm);
        log.info("初始化超级管理员权限成功!");
    }

    @Override
    public SysUserPermVO getUserPerm(SysUserPermDTO params) {
        // 1、拿到用户基础信息
        SysUserPermVO userPerm = this.iSysUserService.getUserPerm(params);

        // 2、权限树
        userPerm.setPermissionTreeList(this.tree(userPerm.getRoleIdList(), true));

        userPerm.handleData();
        return userPerm;
    }

    @Override
    public SysRoleAllPermissionDetailVO permissionDetail(Integer roleId) {
        // 1、角色基本信息
        SysRole sysRole = this.iSysRoleService.getById(roleId);
        Assert.notNull(sysRole, "角色不存在！");

        // 2、菜单权限树
        List<SysMenuTree> menuTree = this.tree(Lists.newArrayList(roleId), false);

        return SysRoleAllPermissionDetailVO.builder()
                .roleId(sysRole.getRoleId())
                .name(sysRole.getName())
                .code(sysRole.getCode())
                .status(sysRole.getStatus())
                .menuTree(menuTree)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshRedisPerm() {
        // 1、先删除缓存
        RedisUtil.delete(SecurityConstant.URL_PERM_RE_ROLES);

        // 2、查询角色关联权限数据
        List<SysRoleRePermListVO> roleRePermList = this.iSysPermissionService.listRoleRePerm();
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
    public void delTenantRePerm(Integer tenantId, List<Integer> delMenuIdList, List<Integer> delPermIdList) {
        if (!AppConstant.TENANT_ID_SMALL_BOOT.equals(tenantId)) {
            // 更新非系统租户数据
            this.iSysRoleMenuService.delReMenuId(tenantId, delMenuIdList);
            this.iSysRolePermissionService.delByPermId(tenantId, delPermIdList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleRePerm(SysRoleRePermSaveDTO params) {
        Integer roleId = params.getRoleId();

        // 1、先保存角色关联的菜单权限
        this.iSysRoleMenuService.saveRoleMenuIds(
                SysRoleReMenuSaveDTO.builder()
                        .roleId(roleId)
                        .menuIdList(params.getMenuIdList())
                        .build()
        );


        // 2、再保存角色关联的按钮权限
        this.saveRoleRePermIds(
                SysRoleRePermIdsSaveDTO.builder()
                        .roleId(roleId)
                        .permissionIdList(params.getPermissionIdList())
                        .build()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleRePermIds(SysRoleRePermIdsSaveDTO params) {
        this.iSysRolePermissionService.savePerm(params);
    }


    @Override
    public List<SysMenuTree> tree(List<Integer> roleIdList, boolean isOnlyShowPerm) {
        // 1、拿到所有菜单
        List<SysMenuTree> allMenuList = this.iSysMenuService.selectMenuTree(roleIdList, isOnlyShowPerm);

        // 2、全部url/btn权限
        Map<Integer, List<SysRoleRePermVO>> mapPerm = this.iSysPermissionService.mapPermByRole(roleIdList, isOnlyShowPerm);

        // 3、遍历出父菜单对应的子菜单 -- 递归
        return this.recurveMenu(AppConstant.PARENT_ID, allMenuList, mapPerm);
    }

    /**
     * 递归菜单
     *
     * @param parentMenuId 父菜单id
     * @param allMenuList  所有菜单
     * @param mapPerm      全部url/btn权限
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysMenuTree> recurveMenu(Integer parentMenuId, List<SysMenuTree> allMenuList, Map<Integer, List<SysRoleRePermVO>> mapPerm) {
        // 存放子菜单的集合
        List<SysMenuTree> childMenuList = allMenuList.stream().filter(e -> e.getParentId().equals(parentMenuId)).collect(Collectors.toList());

        // 递归
        childMenuList.forEach(item -> {
            Integer menuId = item.getMenuId();
            // 权限
            item.setPermList(mapPerm.get(menuId));
            // 子菜单
            item.setChildren(this.recurveMenu(menuId, allMenuList, mapPerm));
            item.handleData();
        });
        return childMenuList;
    }

}
