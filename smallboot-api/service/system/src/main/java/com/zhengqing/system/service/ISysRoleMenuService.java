package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysRoleMenu;
import com.zhengqing.system.model.dto.SysRoleReMenuSaveDTO;
import com.zhengqing.system.model.vo.SysRoleReBtnPermListVO;

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
    void saveRoleMenuIds(SysRoleReMenuSaveDTO params);


    /**
     * 根据角色id删除角色对应的所有关联菜单
     *
     * @param roleId 角色id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:08
     */
    void delByRoleId(Integer roleId);

    /**
     * 获取租户可访问的菜单ids
     *
     * @return 可访问的菜单ids
     * @author zhengqingya
     * @date 2020/9/10 18:09
     */
    List<Integer> getMenuIdList(Integer tenantId);

    /**
     * 根据菜单id删除关联权限
     *
     * @param delMenuIdList 菜单id
     * @return void
     * @author zhengqingya
     * @date 2023/10/8 19:18
     */
    void delReMenuIdList(List<Integer> delMenuIdList);

    /**
     * 查询按钮权限
     *
     * @return 按钮权限
     * @author zhengqingya
     * @date 2023/10/8 19:18
     */
    List<SysRoleReBtnPermListVO> listRoleReBtnPerm();

}
