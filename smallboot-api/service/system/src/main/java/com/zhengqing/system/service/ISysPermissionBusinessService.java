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
     * 刷新Redis缓存中的角色菜单权限
     */
    void refreshRedisPerm();

}
