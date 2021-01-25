package com.ind4fibre.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ind4fibre.exception.NotFoundException;

@ControllerAdvice
public class ConfiguracaoSpringException extends ResponseEntityExceptionHandler {

    public ConfiguracaoSpringException() {
        super();
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<?> handleUserNotFoundException(NotFoundException ex, WebRequest request) {
      return ResponseEntity.notFound().build();
    }
    
}
