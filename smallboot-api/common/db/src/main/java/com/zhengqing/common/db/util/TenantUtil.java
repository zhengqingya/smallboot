package com.zhengqing.common.db.util;


import com.zhengqing.common.base.context.TenantIdContext;

import java.util.concurrent.Callable;


/**
 * <p> 租户工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/7/22 20:48
 */
public class TenantUtil {

    /**
     * 指定租户执行业务逻辑
     *
     * @param tenantId 租户ID
     * @param runnable 业务逻辑
     * @return void
     * @author zhengqingya
     */
    public static void executeByTenantId(Integer tenantId, Runnable runnable) {
        Integer oldTenantId = TenantIdContext.getTenantId();
        Boolean isFlag = TenantIdContext.getFlag();
        try {
            TenantIdContext.setTenantId(tenantId);
            runnable.run();
        } finally {
            TenantIdContext.setTenantId(oldTenantId);
            TenantIdContext.setFlag(isFlag);
        }
    }

    /**
     * 指定租户执行业务逻辑
     *
     * @param tenantId 租户ID
     * @param callable 业务逻辑
     * @return 执行结果
     * @author zhengqingya
     * @date 2023/10/9 13:58
     */
    public static <V> V executeByTenantId(Integer tenantId, Callable<V> callable) {
        Integer oldTenantId = TenantIdContext.getTenantId();
        Boolean isFlag = TenantIdContext.getFlag();
        try {
            TenantIdContext.setTenantId(tenantId);
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            TenantIdContext.setTenantId(oldTenantId);
            TenantIdContext.setFlag(isFlag);
        }
    }

    /**
     * 执行业务逻辑(忽略租户)，有租户的情况下，执行完业务返回之前租户信息
     *
     * @param runnable 业务逻辑
     * @return void
     * @author zhengqingya
     */
    public static void executeRemoveFlag(Runnable runnable) {
        Integer oldTenantId = TenantIdContext.getTenantId();
        Boolean isFlag = TenantIdContext.getFlag();
        try {
            TenantIdContext.removeFlag();
            runnable.run();
        } finally {
            TenantIdContext.setTenantId(oldTenantId);
            TenantIdContext.setFlag(isFlag);
        }
    }

    /**
     * 执行业务逻辑(忽略租户)，有租户的情况下，执行完业务返回之前租户信息
     *
     * @param callable 业务逻辑
     * @return 执行结果
     * @return void
     * @author zhengqingya
     */
    public static <V> V executeRemoveFlag(Callable<V> callable) {
        Integer oldTenantId = TenantIdContext.getTenantId();
        Boolean isFlag = TenantIdContext.getFlag();
        try {
            TenantIdContext.removeFlag();
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            TenantIdContext.setTenantId(oldTenantId);
            TenantIdContext.setFlag(isFlag);
        }
    }

}
