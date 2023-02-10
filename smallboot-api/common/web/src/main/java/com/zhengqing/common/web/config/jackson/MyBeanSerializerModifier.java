package com.zhengqing.common.web.config.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.Collection;
import java.util.List;


/**
 * <p> Jackson序列化修改器 </p>
 *
 * @author zhengqingya
 * @description SpringBoot返回Json数据中null值处理
 * @date 2022/7/26 9:51
 */
public class MyBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (Object beanProperty : beanProperties) {
            BeanPropertyWriter writer = (BeanPropertyWriter) beanProperty;
            // 根据字段的类型进行处理
            if (this.isArrayType(writer)) {
                // 将集合数组类型null值转换为[]
                writer.assignNullSerializer(new CustomNullJsonSerializer.NullArrayJsonSerializer());
            } else if (this.isNumberType(writer)) {
                // 将数字类型null值转换为0
//                writer.assignNullSerializer(new CustomNullJsonSerializer.NullNumberJsonSerializer());
            } else if (this.isBooleanType(writer)) {
                // 将布尔类型null值转换为false
                writer.assignNullSerializer(new CustomNullJsonSerializer.NullBooleanJsonSerializer());
            } else if (this.isStringType(writer)) {
                // 将字符串类型null值转换为""
                writer.assignNullSerializer(new CustomNullJsonSerializer.NullStringJsonSerializer());
            }
        }
        return beanProperties;
    }

    /**
     * 是否是数组
     */
    private boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是string
     */
    private boolean isStringType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz);
    }


    /**
     * 是否是int
     */
    private boolean isNumberType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return Number.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是boolean
     */
    private boolean isBooleanType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(Boolean.class);
    }

}
