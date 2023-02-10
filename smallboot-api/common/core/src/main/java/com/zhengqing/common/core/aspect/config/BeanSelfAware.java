package com.zhengqing.common.core.aspect.config;

/**
 * <p>
 * 定义BeanPostProcessor 需要使用的标识接口
 * </p>
 *
 * @author zhengqingya
 * @description 即我们自定义的BeanPostProcessor （InjectBeanSelfProcessor）如果发现我们的Bean是实现了该标识接口就调用setSelf注入代理对象
 * @date 2021/1/9 1:10
 */
public interface BeanSelfAware {
    void setSelf(Object proxyBean);
}
