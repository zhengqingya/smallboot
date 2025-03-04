package com.zhengqing.common.base.constant;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p> 权限认证 常用变量 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/10/12 14:47
 */
public interface AuthConstant extends BaseConstant {

    /**
     * 超级管理员 -- 用户id、角色id
     */
    Integer SYSTEM_SUPER_ADMIN_USER_ID = 1;
    Integer SMALL_BOOT_SUPER_ADMIN_ROLE_ID = 1;
    // 系统管理员 -- 用户id、角色id
    Integer SMALL_BOOT_SYSTEM_ADMIN_USER_ID = 2;
    Integer SMALL_BOOT_SYSTEM_ADMIN_ROLE_ID = 2;
    // 租户套餐 变更时  不清除的角色关联菜单数据
    List<Integer> NOT_DEL_MENU_EXCLUDE_ROLE_ID_LIST = Lists.newArrayList(1, 2);

}
