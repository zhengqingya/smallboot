package com.zhengqing.common.web.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * <p> jackson 转Long类型 </p>
 *
 * @author zhengqingya
 * @description 解决全局将Long类型转换为String类型后，部分类又需要Long还是原本Long类型问题
 * 使用方式：添加注解`@JsonSerialize(using = ToLongSerializer.class)`
 * @date 2022/8/5 17:22
 */
public class ToLongSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value);
    }

}