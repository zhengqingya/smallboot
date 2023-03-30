package com.zhengqing.system.service;

import com.zhengqing.system.model.dto.SysRoleRePermIdsSaveDTO;
import com.zhengqing.system.model.dto.SysRoleRePermSaveDTO;

/**
 * <p>
 * 系统管理 - 权限系列缓存 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
public interface ISysPermBusinessService {

    /**
     * 初始化超级管理员权限
     *
     * @return void
     * @author zhengqingya
     * @date 2023/3/28 11:24
     */
    void initSuperAdminPerm();

    /**
     * 刷新Redis缓存中的角色菜单权限
     *
     * @return void
     * @author zhengqingya
     * @date 2023/3/28 11:24
     */
    void refreshRedisPerm();


    /**
     * 保存角色关联菜单按钮权限ids
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 18:34
     */
    void saveRoleRePermIds(SysRoleRePermIdsSaveDTO params);

    /**
     * 保存角色权限（菜单权限+按钮权限）
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 15:01
     */
    void saveRoleRePerm(SysRoleRePermSaveDTO params);

}
