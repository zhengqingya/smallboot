package com.zhengqing.system.service;

import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.bo.SysRoleRePermSaveBO;
import com.zhengqing.system.model.dto.SysMenuTreeDTO;
import com.zhengqing.system.model.dto.SysRoleReScopeSaveDTO;
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
     * 刷新超级管理员权限
     *
     * @return void
     * @author zhengqingya
     * @date 2023/3/28 11:24
     */
    void refreshSuperAdminPerm();

    /**
     * 刷新系统租户权限
     *
     * @return void
     * @author zhengqingya
     * @date 2023/3/28 11:24
     */
    void refreshSysTenantRePerm();

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
     * 刷新租户关联的菜单按钮权限，删除旧权限中不存在于最新套餐权限中的权限
     *
     * @param tenantId 租户id
     * @return void
     * @author zhengqingya
     * @date 2023/3/28 11:24
     */
    void refreshTenantRePerm(Integer tenantId);

    /**
     * 保存角色权限（菜单权限+按钮权限）
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 15:01
     */
    void saveRoleRePerm(SysRoleRePermSaveBO params);

    /**
     * 保存角色权限（数据权限）
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 15:01
     */
    void saveRoleReScope(SysRoleReScopeSaveDTO params);

    /**
     * 获取菜单树 -- 根据租户获取
     *
     * @param params 提交参数
     * @return 菜单树信息
     * @author zhengqingya
     * @date 2021/1/13 20:44
     */
    List<SysMenuTree> tree(SysMenuTreeDTO params);

}
