package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysRolePermission;
import com.zhengqing.system.model.dto.SysRoleMenuBtnSaveDTO;
import com.zhengqing.system.model.vo.SysRoleMenuBtnListVO;

import java.util.List;

/**
 * <p>
 * 系统管理 - 角色关联权限服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 获取所有角色菜单按钮权限信息
     *
     * @return 所有角色关联的菜单按钮信息
     * @author zhengqingya
     * @date 2020/9/10 17:54
     */
    List<SysRoleMenuBtnListVO> listRoleMenuBtn();


    /**
     * 通过角色ID和菜单ID查询该菜单所拥有的所有按钮权限
     *
     * @param roleId 角色ID
     * @param menuId 菜单ID
     * @return 菜单所拥有的所有按钮权限ids
     * @author zhengqingya
     * @date 2020/9/10 17:58
     */
    List<Integer> getPermissionBtnsByRoleIdAndMenuId(Integer roleId, Integer menuId);

    /**
     * 根据角色id删除关联所有按钮权限
     *
     * @param roleId 角色id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 17:50
     */
    void deleteBtnsByRoleId(Integer roleId);

    /**
     * 根据角色ID和菜单ID删除按钮
     *
     * @param roleId 角色ID
     * @param menuId 菜单ID
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 17:57
     */
    void deleteBtnsByRoleIdAndMenuId(Integer roleId, Integer menuId);

    /**
     * 保存角色关联菜单按钮ids
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:34
     */
    void saveRoleMenuBtnIds(SysRoleMenuBtnSaveDTO params);

}
