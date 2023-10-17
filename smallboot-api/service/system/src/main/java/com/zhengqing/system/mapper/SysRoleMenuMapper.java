package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysRoleMenu;
import com.zhengqing.system.model.vo.SysRoleReBtnPermListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统管理-角色菜单表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 19:00
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 批量保存角色菜单对应关系
     *
     * @param roleId     角色id
     * @param menuIdList 菜单ids
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:08
     */
    void batchInsertRoleMenuIds(@Param("roleId") Integer roleId, @Param("menuIdList") List<Integer> menuIdList);

    /**
     * 根据角色id删除角色对应的所有关联菜单
     *
     * @param roleId 角色id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:08
     */
    @Delete("DELETE FROM t_sys_role_menu WHERE role_id = #{roleId}")
    void deleteAllMenusByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取角色id可访问的菜单ids
     *
     * @param roleId 角色id
     * @return 可访问的菜单ids
     * @author zhengqingya
     * @date 2020/9/10 18:09
     */
    @Select("SELECT menu_id FROM t_sys_role_menu WHERE role_id = #{roleId} ORDER BY menu_id")
    List<Integer> selectMenuIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取租户可访问的菜单ids
     *
     * @return 可访问的菜单ids
     * @author zhengqingya
     * @date 2020/9/10 18:09
     */
    @Select("SELECT DISTINCT menu_id FROM t_sys_role_menu WHERE tenant_id = #{tenantId}")
    List<Integer> selectMenuIdList(@Param("tenantId") Integer tenantId);

    /**
     * 获取角色ids可访问的菜单ids
     *
     * @param roleIdList 角色ids
     * @return 可访问的菜单ids
     * @author zhengqingya
     * @date 2020/9/10 18:09
     */
    List<Integer> selectMenuIdsByRoleIds(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 查询按钮权限
     *
     * @return 按钮权限
     * @author zhengqingya
     * @date 2023/10/8 19:18
     */
    List<SysRoleReBtnPermListVO> selectBtnPerm();

}
