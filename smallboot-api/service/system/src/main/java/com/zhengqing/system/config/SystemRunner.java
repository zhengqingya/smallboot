package com.zhengqing.system.config;

import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.core.config.AppCommonRunner;
import com.zhengqing.system.service.ISysConfigService;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysPermBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 服务初始化之后，执行方法
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/5/22 19:29
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemRunner extends AppCommonRunner {

    private final ISysDictService iSysDictService;
    private final ISysPermBusinessService iSysPermBusinessService;
    private final ISysConfigService iSysConfigService;

    @Override
    public void run(String... args) throws Exception {
        super.appRun();

//        log.info("服务初始化之后，执行方法 start...");

        // 拉取 el-icon 图标数据
//        this.dictService.initElIconData();

        TenantIdContext.setTenantId(AppConstant.SMALL_BOOT_TENANT_ID);

        // 数据字典
        this.iSysDictService.initCache();

        // 系统配置
        this.iSysConfigService.initCache();

        // 刷新超级管理员权限
        this.iSysPermBusinessService.refreshSuperAdminPerm();
        // 刷新系统租户权限
        this.iSysPermBusinessService.refreshSysTenantRePerm();

        // 权限缓存
        this.iSysPermBusinessService.refreshRedisPerm();

//        log.info("服务初始化之后，执行方法 end...");
    }

}
