package com.zhengqing.cms.config;

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
public class CmsMybatisPlusConfig {

    static {
        MybatisPlusConfig.TENANT_ID_TABLE.add("cms_job");
        MybatisPlusConfig.TENANT_ID_TABLE.add("cms_job_category");
        MybatisPlusConfig.TENANT_ID_TABLE.add("cms_job_tag");
        MybatisPlusConfig.TENANT_ID_TABLE.add("cms_job_apply");
    }

}
