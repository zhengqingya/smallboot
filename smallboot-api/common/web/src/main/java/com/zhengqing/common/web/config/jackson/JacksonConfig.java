package com.zhengqing.common.web.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * <p> Jackson配置 </p>
 *
 * @author zhengqingya
 * @description tips: 继承`WebMvcConfigurationSupport`会导致`application-web.yml`中jackson配置失效
 * @date 2022/7/26 9:53
 */
@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // 注册Factory
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier()));
        // 对象null值转变为{}
//        objectMapper.getSerializerProvider().setNullValueSerializer(new CustomNullJsonSerializer.NullObjectJsonSerializer());

        // 属性命名策略 -> 驼峰式
//        objectMapper.setPropertyNamingStrategy(MyPropertyNamingStrategy.LOWER_CAMEL_CASE_HUMP);

        // Long类型转换为字符串类型(解决前端js展示精度丢失问题)
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

}
