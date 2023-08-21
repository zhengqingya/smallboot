package com.zhengqing.common.core.custom.fileprefix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> 自定义注解-填充文件地址 -- 用于声明要处理的类或list循环数据 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/8 9:31
 */
// 作用到方法上
@Target({ElementType.TYPE_USE, ElementType.FIELD})
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface FilePrefixValid {


}
