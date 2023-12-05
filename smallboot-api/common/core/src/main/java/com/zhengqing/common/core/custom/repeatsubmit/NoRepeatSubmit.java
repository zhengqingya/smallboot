package com.zhengqing.common.core.custom.repeatsubmit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * <p> 自定义注解：校验表单重复提交 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/11/27 9:59
 */
// 作用到方法上
@Target(ElementType.METHOD)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {

    /**
     * 默认时间3秒
     */
    int time() default 3;

    /**
     * 时间单位，默认：秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 默认提示信息
     */
    String msg() default "操作频繁,请稍后再试!";

}
