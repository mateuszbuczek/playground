package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

/** proxy interface using cglib */
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
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(AService.class);
        // fixed value for both methods (will occur class cast exception for lengthOfName method)
        // enhancer.setCallback((FixedValue) () -> "Hello Tom!");
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class ) {
                return "Hello tom!";
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });
        AService proxy = (AService) enhancer.create();

        return proxy;
    }
}
