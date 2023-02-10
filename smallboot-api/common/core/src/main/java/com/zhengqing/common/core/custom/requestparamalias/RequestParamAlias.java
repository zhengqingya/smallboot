package com.zhengqing.common.core.custom.requestparamalias;

import java.lang.annotation.*;

/**
 * <p> 自定义注解`@RequestParamAlias` - get请求`@ModelAttribute`接收对象属性中的字段别名设置 </p>
 *
 * @author zhengqingya
 * @description 通过自定义spring属性编辑器解决
 * tips: 字段别名注解`@JsonProperty`和`@JsonAlias`应用于post请求，不支持get请求，所以这里才单独自定义注解
 * @date 2022/10/20 10:58
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParamAlias {
    String value();
}
