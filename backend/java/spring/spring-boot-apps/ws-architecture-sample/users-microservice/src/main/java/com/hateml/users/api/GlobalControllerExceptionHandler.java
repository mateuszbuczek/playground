package com.hateml.users.api;

import com.hateml.users.api.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<Object> handle(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(getErrorMap(ex.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handle(Exception ex) {
        return new ResponseEntity<>(ErrorResponse.UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, String> getErrorMap(BindingResult result) {
        return result.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
