package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysPermission;
import com.zhengqing.system.model.vo.SysMenuReBtnPermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     * 根据菜单ID查询已经配置的按钮ids
     *
     * @param menuId 菜单id
     * @return 按钮ids
     * @author zhengqingya
     * @date 2020/9/10 21:21
     */
    @Select("SELECT sd.id FROM t_sys_permission sp INNER JOIN t_sys_dict sd on sd.id = sp.btn_id WHERE sp.menu_id = #{menuId}")
    List<Integer> getBtnIdsByMenuId(@Param("menuId") Integer menuId);

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
    List<SysRoleRePermListVO> listRoleRePerm();

}
