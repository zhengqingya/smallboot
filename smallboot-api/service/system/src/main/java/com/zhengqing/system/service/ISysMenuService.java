package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;
import com.zhengqing.system.model.dto.SysMenuTreeDTO;

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
     * 获取所有菜单 -- 菜单树使用
     *
     * @param params 查询参数
     * @return 菜单信息
     * @author zhengqingya
     * @date 2020/9/10 20:30
     */
    List<SysMenuTree> tree(SysMenuTreeDTO params);

    /**
     * 系统所有菜单ids
     *
     * @return 菜单ids
     * @author zhengqingya
     * @date 2021/1/13 20:44
     */
    List<Integer> allMenuId();

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
     * 删除
     *
     * @param id 菜单id
     * @return void
     * @author zhengqingya
     * @date 2021/1/13 20:46
     */
    void deleteData(Integer id);

}
