package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysRoleMenu;
import com.zhengqing.system.model.dto.SysRoleMenuSaveDTO;
import com.zhengqing.system.model.dto.SysRolePermissionSaveDTO;

import java.util.List;

/**
 * <p>
 * 系统管理 - 角色菜单关联服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取角色id可访问的菜单ids
     *
     * @param roleId 角色id
     * @return 可访问的菜单ids
     * @author zhengqingya
     * @date 2020/9/10 18:09
     */
    List<Integer> getMenuIdsByRoleId(Integer roleId);

    /**
     * 获取角色ids可访问的菜单ids
     *
     * @param roleIdList 角色ids
     * @return 可访问的菜单ids
     * @author zhengqingya
     * @date 2020/9/10 18:09
     */
    List<Integer> getMenuIdsByRoleIds(List<Integer> roleIdList);

    /**
     * 保存角色关联菜单ids
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/14 11:15
     */
    void saveRoleMenuIds(SysRoleMenuSaveDTO params);

    /**
     * 保存角色权限（菜单权限+按钮权限）
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 15:01
     */
    void saveRolePermission(SysRolePermissionSaveDTO params);

    /**
     * 根据角色id删除角色对应的所有关联菜单
     *
     * @param roleId 角色id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:08
     */
    void deleteAllMenusByRoleId(Integer roleId);

}
