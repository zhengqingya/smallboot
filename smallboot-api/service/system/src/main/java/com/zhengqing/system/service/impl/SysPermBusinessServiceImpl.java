package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.constant.SecurityConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.entity.SysTenantPackage;
import com.zhengqing.system.enums.SysRoleCodeEnum;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.bo.SysRoleRePermSaveBO;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysRoleAllPermissionDetailVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
    @Lazy
    @Resource
    private ISysTenantService iSysTenantService;
    @Lazy
    @Resource
    private ISysTenantPackageService iSysTenantPackageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshSuperAdminPerm() {
        // 1、先查询超级管理员角色id再绑定
        Integer roleId = this.iSysRoleService.getRoleIdByCode(SysRoleCodeEnum.超级管理员);
        Assert.notNull(roleId, "超级管理员角色丢失！");
        this.iSysUserRoleService.addOrUpdateData(
                SysUserRoleSaveDTO.builder()
                        .userId(AppConstant.SYSTEM_SUPER_ADMIN_USER_ID)
                        .roleIdList(Lists.newArrayList(roleId))
                        .build()
        );

        // 2、先查询所有菜单和按钮数据
        List<SysMenuTree> menuTree = this.treeAll();

        // 3、保存角色关联的菜单和按钮权限
        SysMenuPermBaseDTO permBO = SysMenuPermBaseDTO.builder().menuTree(menuTree).build();
        permBO.handleParam();
        this.saveRoleRePerm(SysRoleRePermSaveBO.builder()
                .roleId(roleId)
                .menuIdList(permBO.getMenuIdList())
                .permissionIdList(permBO.getPermissionIdList())
                .build());
        log.info("刷新超级管理员权限成功!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshSysTenantRePerm() {
        // 1、先查询所有菜单和按钮数据
        List<SysMenuTree> menuTree = this.treeAll();
        SysMenuPermBaseDTO sysMenuPermBaseDTO = SysMenuPermBaseDTO.builder().menuTree(menuTree).build();
        sysMenuPermBaseDTO.handleParam();
        // 2、更新权限
        this.iSysTenantPackageService.updateTenantIdRePerm(AppConstant.SMALL_BOOT_TENANT_ID, sysMenuPermBaseDTO.getMenuIdList(), sysMenuPermBaseDTO.getPermissionIdList());
        log.info("刷新系统租户权限成功!");
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
        List<SysMenuTree> menuTree = this.baseTree(TenantIdContext.getTenantId(), Lists.newArrayList(roleId), false);

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
    public void refreshTenantRePerm(Integer tenantId) {
        // 只更新非系统租户数据
        if (AppConstant.SMALL_BOOT_TENANT_ID.equals(tenantId) || tenantId == null) {
            return;
        }
        TenantIdContext.setTenantId(tenantId);

        // 1、查询旧菜单权限
        List<Integer> menuIdListOld = this.iSysRoleMenuService.getMenuIdList(tenantId);
        List<Integer> permIdListOld = this.iSysRolePermissionService.getPermIdList(tenantId);

        // 2、查询新租户套餐权限
        SysTenantPackage sysTenantPackage = this.iSysTenantPackageService.detailReTenantId(tenantId);

        // 3、拿到要删除的旧权限id
        List<Integer> delMenuIdList = CollUtil.subtractToList(menuIdListOld, sysTenantPackage.getMenuIdList());
        List<Integer> delPermIdList = CollUtil.subtractToList(permIdListOld, sysTenantPackage.getPermissionIdList());

        // 4、删除权限
        this.iSysRoleMenuService.delReMenuId(tenantId, delMenuIdList);
        this.iSysRolePermissionService.delByPermId(tenantId, delPermIdList);

        // 5、给租户管理员默认加上最新的权限
        this.saveRoleRePerm(
                SysRoleRePermSaveBO.builder()
                        .roleId(this.iSysRoleService.getRoleIdByCode(SysRoleCodeEnum.租户管理员))
                        .menuIdList(sysTenantPackage.getMenuIdList())
                        .permissionIdList(sysTenantPackage.getPermissionIdList())
                        .build()
        );
        this.refreshRedisPerm();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleRePerm(SysRoleRePermSaveBO params) {
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
        return this.baseTree(TenantIdContext.getTenantId(), roleIdList, isOnlyShowPerm);
    }

    @Override
    public List<SysMenuTree> treeAll() {
        return this.baseTree(null, null, false);
    }

    /**
     * 获取菜单树
     *
     * @param tenantId       角色ids tips:为空时拿到所有权限
     * @param roleIdList     角色ids tips:为空时拿到所有权限
     * @param isOnlyShowPerm 是否仅显示带权限的数据  false:显示租户下所有权限 true:只显示角色有的权限（适用于用户获取角色权限）
     * @return 菜单树信息
     * @author zhengqingya
     * @date 2021/1/13 20:44
     */
    public List<SysMenuTree> baseTree(Integer tenantId, List<Integer> roleIdList, boolean isOnlyShowPerm) {
        // 1、查询租户权限
        List<Integer> menuIdList = Lists.newArrayList();
        List<Integer> permIdList = Lists.newArrayList();
        if (tenantId != null) {
            SysTenantPackage sysTenantPackage = this.iSysTenantPackageService.detailReTenantId(tenantId);
            menuIdList = sysTenantPackage.getMenuIdList();
            permIdList = sysTenantPackage.getPermissionIdList();
        }

        // 2、拿到所有菜单
        List<SysMenuTree> allMenuList = this.iSysMenuService.selectMenuTree(roleIdList, isOnlyShowPerm, menuIdList);

        // 3、全部url/btn权限
        Map<Integer, List<SysRoleRePermVO>> mapPerm = this.iSysPermissionService.mapPermByRole(roleIdList, isOnlyShowPerm, permIdList);

        // 4、遍历出父菜单对应的子菜单 -- 递归
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
