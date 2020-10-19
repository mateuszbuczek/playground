package com.example.demo;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/** proxy interface using spring's ProxyFactory (JDK dynamic proxy) */
@Component
public class LoggingBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AService) {
            return proxiedBean((AService) bean);
        } else {
            return bean;
        }
    }

    private Object proxiedBean(AService bean) {
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice(new LoggingInterceptor());
        return proxyFactory.getProxy();
    }

    private static class LoggingInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("BEFORE");
            Object returnVal = invocation.proceed();
            System.out.println("AFTER");
            return returnVal;
        }
    }
}
