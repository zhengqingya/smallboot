//package com.zhengqing.common.web.config;
//
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//
///**
// * <p>
// * 配置fastjson规则
// * </p>
// *
// * @author zhengqingya
// * @description Date类型数据转化有两种方式：
// * 第一种就是在配置类中直接定义日期格式（全局统一格式时推荐使用）
// * fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
// * 第二种在字段上进行注解（灵活性大，可根据情况不同字段设置不同的时间格式）
// * -- @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
// * -- @JSONField(format="yyyy-MM-dd HH:mm:ss")
// * -- private Date time;
// * <p>
// * 注：如果存在时差13小时问题，需配置数据库连接参数`&serverTimezone=Asia/Shanghai`
// * @date 2019/8/19 23:08
// */
//@Slf4j
//@Configuration
//public class FastjsonConfig {
//
//    @Bean
//    public HttpMessageConverters fastJsonConfigure() {
//        // 1. 先定义一个convert 转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//
//        // 2. 添加fastJson的配置信息,比如，是否需要格式化返回的json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        // 空值特别处理
//        // WriteNullListAsEmpty 将Collection类型字段的字段空值输出为[]
//        // WriteNullStringAsEmpty 将字符串类型字段的空值输出为空字符串 ""
//        // WriteNullNumberAsZero 将数值类型字段的空值输出为0
//        // WriteNullBooleanAsFalse 将Boolean类型字段的空值输出为false
//        // DisableCircularReferenceDetect 消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteNullListAsEmpty,
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.WriteMapNullValue);
//        // 日期格式化
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        /**
//         * 配置mybatis-plus序列化枚举值为数据库存储值 -- tips:实测不配置这里也可
//         * https://baomidou.com/pages/8390a4/#fastjson
//         */
////        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteEnumUsingToString);
//
//        // 3. 在convert中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        // 4. 处理中文乱码问题
//        fastConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));
//        return new HttpMessageConverters(fastConverter);
//    }
//
//}
