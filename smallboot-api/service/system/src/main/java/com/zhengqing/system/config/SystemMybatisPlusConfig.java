package com.zhengqing.system.config;

import com.zhengqing.common.db.config.mybatis.MybatisPlusConfig;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * MybatisPlus配置类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/23 9:46
 */
@Configuration
public class SystemMybatisPlusConfig {

    static {
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_app_config");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_config");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_dept");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_file");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_post");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_merchant");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_role");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_role_menu");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_role_permission");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_user");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_user_re_oauth");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_user_role");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_sys_version");
    }

}
