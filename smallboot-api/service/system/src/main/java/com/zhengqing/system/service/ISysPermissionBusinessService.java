package com.zhengqing.system.service;

/**
 * <p>
 * 系统管理 - 权限系列缓存 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
public interface ISysPermissionBusinessService {

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

}
