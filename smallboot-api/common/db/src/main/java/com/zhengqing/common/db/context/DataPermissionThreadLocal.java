package com.zhengqing.common.db.context;

import com.zhengqing.common.db.config.mybatis.data.permission.second.UserPermissionInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> 用户数据权限信息上下文 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/10 17:53
 */
@Slf4j
public class DataPermissionThreadLocal {

    private static final ThreadLocal<UserPermissionInfo> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(UserPermissionInfo userPermissionInfo) {
        THREAD_LOCAL.set(userPermissionInfo);
    }

    public static UserPermissionInfo get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
