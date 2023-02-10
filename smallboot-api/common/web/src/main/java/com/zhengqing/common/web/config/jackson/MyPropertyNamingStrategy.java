package com.zhengqing.common.web.config.jackson;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * <p> Jackson自定义属性命名策略 </p>
 *
 * @author zhengqingya
 * @description 驼峰式命名  ex:test_data -> testData
 * tips:原生的不支持下划线转驼峰
 * @date 2022/7/26 16:34
 */
public class MyPropertyNamingStrategy extends PropertyNamingStrategy {

    public static final PropertyNamingStrategy LOWER_CAMEL_CASE_HUMP = new LowerCamelCaseStrategy2();

    public static class LowerCamelCaseStrategy2 extends PropertyNamingStrategies.NamingBase {
        private static final long serialVersionUID = 2L;

        @Override
        public String translate(String propertyName) {
            return StrUtil.toCamelCase(propertyName);
        }
    }
}
