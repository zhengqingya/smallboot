package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.model.dto.SysMenuListDTO;
import com.zhengqing.system.model.vo.SysMenuTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统管理-菜单表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 列表分页
     *
     * @param page   分页
     * @param filter 过滤参数
     * @return 菜单信息
     * @author zhengqingya
     * @date 2020/9/10 20:29
     */
    IPage<SysMenu> selectMenus(IPage<SysMenu> page, @Param("filter") SysMenuListDTO filter);

    /**
     * 列表
     *
     * @param filter 过滤参数
     * @return 菜单信息
     * @author zhengqingya
     * @date 2020/9/10 20:29
     */
    List<SysMenu> selectMenus(@Param("filter") SysMenuListDTO filter);

    /**
     * 获取所有菜单 - 仅菜单树使用
     *
     * @param roleId 角色ID
     * @return 菜单信息
     * @author zhengqingya
     * @date 2020/9/10 20:30
     */
    List<SysMenuTreeVO> selectMenuTree(@Param("roleId") Integer roleId);

}
