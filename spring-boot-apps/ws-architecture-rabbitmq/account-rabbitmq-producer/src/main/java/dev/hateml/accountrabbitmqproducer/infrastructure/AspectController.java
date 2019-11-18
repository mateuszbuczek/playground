package dev.hateml.accountrabbitmqproducer.infrastructure;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectController {

    Counter accountCreatedCounter = Metrics.counter("dev.hateml.account.created");

    @Before("execution(public = dev.hateml.interfaces.*Controller.*(..))")
    public void generalAllMethodAspect() {
        // all method calls invokes this aspect
    }

    @AfterReturning("execution(public = dev.hateml.interfaces.*Controller.createAccount(..))")
    public void getCalledOnAccountSave() {
        // createAccount method calls invokes this aspect
        accountCreatedCounter.increment();
    }
}
