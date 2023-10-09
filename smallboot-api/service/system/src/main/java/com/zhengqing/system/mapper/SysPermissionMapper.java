package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysPermission;
import com.zhengqing.system.model.vo.SysMenuReBtnPermListVO;
import com.zhengqing.system.model.vo.SysMenuRePermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统管理-权限 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 19:00
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据菜单id删除所属的按钮ids
     *
     * @param menuId 菜单id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 21:36
     */
    @Delete("DELETE FROM t_sys_permission WHERE menu_id = #{menuId}")
    void deleteByMenuId(@Param("menuId") Integer menuId);

    /**
     * 通过菜单id查询菜单按钮权限信息
     *
     * @param menuId 菜单id
     * @return 菜单按钮权限信息
     * @author zhengqingya
     * @date 2020/9/10 22:18
     */
    List<SysMenuReBtnPermListVO> selectBtnInfoListByMenuId(@Param("menuId") Integer menuId);

    /**
     * 获取角色权限映射数据
     *
     * @return 权限
     * @author zhengqingya
     * @date 2022/6/14 14:55
     */
    List<SysRoleRePermListVO> selectListRoleRePerm();

    /**
     * 获取菜单对应的权限数据
     *
     * @return 菜单ID -> btn/url权限
     * @author zhengqingya
     * @date 2022/6/14 14:55
     */
    List<SysMenuRePermListVO> selectListMenuRePerm();

    /**
     * 全部url/btn权限 & 角色是否具有此权限
     *
     * @param roleIdList     角色ids
     * @param isOnlyShowPerm 是否仅显示带权限的数据
     * @param permIdList     权限ids
     * @return 权限
     * @author zhengqingya
     * @date 2022/6/14 14:55
     */
    List<SysRoleRePermVO> selectListPermByRole(@Param("roleIdList") List<Integer> roleIdList, @Param("isOnlyShowPerm") boolean isOnlyShowPerm, @Param("permIdList") List<Integer> permIdList);

}
