package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Scope(value = "singleton")
public class ABean implements InitializingBean {

    private final Environment environment;

    public ABean(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void afterConstruct() {
        System.out.println(">>>>>>>>>>>>>>>>> Bean, post construct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println(">>>>>>>>>>>>>>>>> Bean, pre destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>> Bean, after properties set");
    }
}
