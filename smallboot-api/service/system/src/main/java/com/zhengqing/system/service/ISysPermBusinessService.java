package com.zhengqing.system.service;

import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysRoleRePermIdsSaveDTO;
import com.zhengqing.system.model.dto.SysRoleRePermSaveDTO;
import com.zhengqing.system.model.dto.SysUserPermDTO;
import com.zhengqing.system.model.vo.SysRoleAllPermissionDetailVO;
import com.zhengqing.system.model.vo.SysUserPermVO;

import java.util.List;

/**
 * <p>
 * 系统管理 - 权限系列缓存 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
public interface ISysPermBusinessService {

    /**
     * 初始化超级管理员权限
     *
     * @return void
     * @author zhengqingya
     * @date 2023/3/28 11:24
     */
    void initSuperAdminPerm();

    /**
     * 获取用户的基本信息+角色+权限...
     *
     * @param params 查询参数
     * @return 用户权限信息
     * @author zhengqingya
     * @date 2020/9/21 16:18
     */
    SysUserPermVO getUserPerm(SysUserPermDTO params);

    /**
     * 根据角色ID获取角色信息详情（含角色基本信息+菜单信息+按钮信息）
     *
     * @param roleId 角色id
     * @return 角色权限具体详情信息
     * @author zhengqingya
     * @date 2020/9/11 16:16
     */
    SysRoleAllPermissionDetailVO permissionDetail(Integer roleId);

    /**
     * 刷新Redis缓存中的角色菜单权限
     *
     * @return void
     * @author zhengqingya
     * @date 2023/3/28 11:24
     */
    void refreshRedisPerm();

    /**
     * 刷新租户关联的菜单按钮权限
     *
     * @param tenantId         租户id
     * @param menuIdList       菜单权限
     * @param permissionIdList 按钮权限
     * @return void
     * @author zhengqingya
     * @date 2023/3/28 11:24
     */
    void refreshTenantRePerm(Integer tenantId, List<Integer> menuIdList, List<Integer> permissionIdList);

    /**
     * 保存角色权限（菜单权限+按钮权限）
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 15:01
     */
    void saveRoleRePerm(SysRoleRePermSaveDTO params);

    /**
     * 保存角色关联菜单按钮权限ids
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:34
     */
    void saveRoleRePermIds(SysRoleRePermIdsSaveDTO params);

    /**
     * 获取菜单树
     *
     * @param roleIdList     角色ids tips:为空时拿到所有权限
     * @param isOnlyShowPerm 是否仅显示带权限的数据  false:显示所有权限
     * @return 菜单树信息
     * @author zhengqingya
     * @date 2021/1/13 20:44
     */
    List<SysMenuTree> tree(List<Integer> roleIdList, boolean isOnlyShowPerm);

}
