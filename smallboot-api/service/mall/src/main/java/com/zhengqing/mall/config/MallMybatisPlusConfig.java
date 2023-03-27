package com.zhengqing.mall.config;

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
public class MallMybatisPlusConfig {

    static {
        MybatisPlusConfig.TENANT_ID_TABLE.add("oms_logistic");
        MybatisPlusConfig.TENANT_ID_TABLE.add("oms_order");
        MybatisPlusConfig.TENANT_ID_TABLE.add("oms_order_after_sale");
        MybatisPlusConfig.TENANT_ID_TABLE.add("oms_order_after_sale_item");
        MybatisPlusConfig.TENANT_ID_TABLE.add("oms_order_item");
        MybatisPlusConfig.TENANT_ID_TABLE.add("oms_order_shipping");
        MybatisPlusConfig.TENANT_ID_TABLE.add("oms_order_shipping_item");
        MybatisPlusConfig.TENANT_ID_TABLE.add("pms_attr_key");
        MybatisPlusConfig.TENANT_ID_TABLE.add("pms_attr_value");
        MybatisPlusConfig.TENANT_ID_TABLE.add("pms_category");
        MybatisPlusConfig.TENANT_ID_TABLE.add("pms_category_attr_relation");
        MybatisPlusConfig.TENANT_ID_TABLE.add("pms_category_spu_relation");
        MybatisPlusConfig.TENANT_ID_TABLE.add("pms_spu");
        MybatisPlusConfig.TENANT_ID_TABLE.add("pms_spu_rate");
        MybatisPlusConfig.TENANT_ID_TABLE.add("pms_spu_rate_reply_relation");
        MybatisPlusConfig.TENANT_ID_TABLE.add("pms_sku");
    }

}
