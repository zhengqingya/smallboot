package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysPermission;
import com.zhengqing.system.model.dto.SysMenuReBtnPermSaveDTO;
import com.zhengqing.system.model.vo.SysMenuReBtnPermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;

import java.util.List;

/**
 * <p>
 * 系统管理-权限 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 保存菜单按钮ids
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 21:10
     */
    void addOrUpdateData(SysMenuReBtnPermSaveDTO params);

    /**
     * 通过菜单id查询菜单按钮权限信息
     *
     * @param menuId 菜单id
     * @return 菜单按钮权限信息
     * @author zhengqingya
     * @date 2020/9/10 22:06
     */
    List<SysMenuReBtnPermListVO> getPermListByMenuId(Integer menuId);

    /**
     * 获取角色权限映射数据
     *
     * @return 权限
     * @author zhengqingya
     * @date 2022/6/14 14:55
     */
    List<SysRoleRePermListVO> listRoleRePerm();

}
