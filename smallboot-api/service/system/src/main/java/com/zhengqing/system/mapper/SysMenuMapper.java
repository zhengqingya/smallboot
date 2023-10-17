package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysMenuTreeDTO;
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
     * 获取所有菜单 -- 菜单树使用
     *
     * @param filter 过滤参数
     * @return 菜单信息
     * @author zhengqingya
     * @date 2020/9/10 20:30
     */
    List<SysMenuTree> selectMenuTree(@Param("filter") SysMenuTreeDTO filter);

    /**
     * 系统所有菜单ids
     *
     * @return 菜单ids
     * @author zhengqingya
     * @date 2021/1/13 20:44
     */
    List<Integer> selectAllMenuId();

}
