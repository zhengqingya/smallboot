package com.zhengqing.wxmp.config;

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
public class WxMpMybatisPlusConfig {

    static {
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_wx_mp_account");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_wx_mp_msg_auto_reply");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_wx_mp_template_msg");
        MybatisPlusConfig.TENANT_ID_TABLE.add("t_wx_mp_user");
    }

}
