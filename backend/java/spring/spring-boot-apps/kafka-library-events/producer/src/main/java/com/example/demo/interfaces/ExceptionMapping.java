package com.example.demo.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionMapping {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleRequestBody(MethodArgumentNotValidException ex) {
        String collect = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " - " + error.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(", "));

        log.info("errorMessage: {}", collect);

        return new ResponseEntity<>(collect, HttpStatus.BAD_REQUEST);
    }
}
