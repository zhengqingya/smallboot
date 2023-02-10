package com.zhengqing.common.core.aspect.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 通过BeanPostProcessor 在目标对象中注入代理对象 -> 解决Spring AOP不拦截对象内部调用方法问题
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/9 0:55
 */
@Slf4j
@Component
public class InjectBeanSelfProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 1、注入ApplicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 2、如果Bean没有实现BeanSelfAware标识接口 跳过
        if (!(bean instanceof BeanSelfAware)) {
            return bean;
        }

        log.debug("inject proxy：【{}】", bean.getClass());

        if (AopUtils.isAopProxy(bean)) {
            // 3、如果当前对象是AOP代理对象，直接注入
            ((BeanSelfAware) bean).setSelf(bean);
        } else {
            // 4、如果当前对象不是AOP代理，则通过context.getBean(beanName)获取代理对象并注入
            // 此种方式不适合解决prototype Bean的代理对象注入
            ((BeanSelfAware) bean).setSelf(this.applicationContext.getBean(beanName));
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
