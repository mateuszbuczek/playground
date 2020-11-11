package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    static boolean postProcessBefore = true;
    static boolean postProcessAfter = true;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (postProcessBefore && beanName.contains("AutoConfiguration")) {
            System.out.println(">>>>>>>>>>>>>>> BeanPostProcessor, process bean before initialization. Available bean and beanName");
            postProcessBefore = false;
        };
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        Here libraries use jdk proxy or cglib proxy to apply proxy
        if (postProcessAfter && beanName.contains("AutoConfiguration")) {
            System.out.println(">>>>>>>>>>>>>>> BeanPostProcessor, process bean after initialization. Available bean and beanName");
            postProcessAfter = false;
        };
        return bean;
    }
}
