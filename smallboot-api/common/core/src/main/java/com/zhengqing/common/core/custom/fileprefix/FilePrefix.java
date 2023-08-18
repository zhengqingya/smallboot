package com.zhengqing.common.core.custom.fileprefix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> 自定义注解-填充文件地址 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/8 9:31
 */
// 作用到方法上
@Target(ElementType.FIELD)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface FilePrefix {


}
