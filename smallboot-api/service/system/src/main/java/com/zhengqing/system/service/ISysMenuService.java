package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysMenuListDTO;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;

import java.util.List;

/**
 * <p>
 * 系统管理-菜单表 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return 菜单信息
     * @author zhengqingya
     * @date 2020/9/10 19:04
     */
    IPage<SysMenu> listPage(SysMenuListDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 菜单信息
     * @author zhengqingya
     * @date 2020/9/10 19:18
     */
    List<SysMenu> list(SysMenuListDTO params);

    /**
     * 新增或更新
     *
     * @param params 提交参数
     * @return 菜单id
     * @author zhengqingya
     * @date 2021/1/13 20:46
     */
    Integer addOrUpdateData(SysMenuSaveDTO params);

    /**
     * 获取菜单树
     *
     * @param roleId 角色ID
     * @return 菜单树信息
     * @author zhengqingya
     * @date 2021/1/13 20:44
     */
    List<SysMenuTree> tree(Integer roleId);

}
