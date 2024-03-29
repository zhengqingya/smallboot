package com.zhengqing.wf.config;

import com.zhengqing.common.db.config.mybatis.MybatisPlusConfig;
import org.springframework.context.annotation.Configuration;

/**
 * <p> MybatisPlus配置类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/23 9:46
 */
@Configuration
public class WfMybatisPlusConfig {

    static {
        MybatisPlusConfig.TENANT_ID_TABLE.add("wf_category");
        MybatisPlusConfig.TENANT_ID_TABLE.add("wf_copy");
        MybatisPlusConfig.TENANT_ID_TABLE.add("wf_deploy_form");
        MybatisPlusConfig.TENANT_ID_TABLE.add("wf_form");
    }

}
