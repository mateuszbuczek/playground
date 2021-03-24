package dev.hateml.accountrabbitmqproducer.interfaces;

import dev.hateml.accountrabbitmqproducer.domain.BadRequestException;
import dev.hateml.accountrabbitmqproducer.domain.NotFoundException;
import dev.hateml.accountrabbitmqproducer.interfaces.error.ErrorResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractController implements ApplicationEventPublisherAware {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected ApplicationEventPublisher applicationEventPublisher;

    protected static final String DEFAULT_PAGE_SIZE = "20";

    protected static final String DEFAULT_PAGE_NUMBER = "0";

    Counter http400ExceptionCounter = Metrics.counter("dev.hateml.interfaces.AccountController.HTTP400");

    Counter http404ExceptionCounter = Metrics.counter("dev.hateml.interfaces.AccountController.HTTP404");

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handle(NotFoundException ex) {
        http404ExceptionCounter.increment();
        return new ResponseEntity<>(ErrorResponse.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handle(BadRequestException ex) {
        http400ExceptionCounter.increment();
        return new ResponseEntity<>(ErrorResponse.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public static <T> T checkResourceFound(final T resource) {
        if (resource == null) {
            throw new NotFoundException();
        }
        return resource;
    }
}

