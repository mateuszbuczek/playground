package com.example.demo.translator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TranslatorAspect {

    /* pointcut */
    @Pointcut("execution(BlogText create(String))")
    private void onCreate() { }

    /*advice with pointcut*/
    @Around("onCreate()")
    public Object aroundCreation(ProceedingJoinPoint joinPoint) throws Throwable {
        BlogText proceed = (BlogText) joinPoint.proceed();
        proceed.setText("replaced-text");
        return proceed;
    }
}
