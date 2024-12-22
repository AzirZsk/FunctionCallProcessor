package org.github.azirzsk.fcp.spring.scan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author zhangshukun
 * @since 2024/12/16
 */
@Slf4j
public class ToolScanner implements BeanPostProcessor {



    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        return bean;
    }
}
