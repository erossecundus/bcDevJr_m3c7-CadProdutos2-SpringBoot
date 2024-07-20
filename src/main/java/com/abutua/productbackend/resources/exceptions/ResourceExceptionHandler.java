package com.abutua.productbackend.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrors> validationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
    ValidationErrors error = new ValidationErrors();

    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

    error.setError("Validation Error");
    error.setMessage(exception.getMessage());
    error.setPath(request.getRequestURI());
    error.setStatus(status.value());
    error.setTimeStamp(Instant.now());

    exception.getBindingResult()
              .getFieldErrors()
              .forEach(e -> error.addError(e.getDefaultMessage()));

    return ResponseEntity.status(status).body(error);
  }
}
