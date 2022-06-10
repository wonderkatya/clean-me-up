package com.effcode.clean.me.rest.controller;

import static java.util.Optional.ofNullable;

import com.effcode.clean.me.rest.model.ErrorData;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

//todo list of errors for production, handle security exceptions
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

  @ExceptionHandler({BindException.class, WebExchangeBindException.class})
  public ResponseEntity<ErrorData> handleRequestBodyValidationException(Exception ex, BindingResult bindingResult) {

    log.error("Validation exception: [{}].", ex.getMessage(), ex);
    final ErrorData errorResponse = ErrorData.builder()
        .timestamp(LocalDateTime.now())
        .message(ofNullable(bindingResult.getFieldError())
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .orElse("Validation exception occurred."))
        .build();
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorData> handleGenericError(Exception exception) {

    log.error("Generic exception: [{}].", exception.getMessage(), exception);
    final ErrorData errorResponse = ErrorData.builder()
        .timestamp(LocalDateTime.now())
        .message(exception.getMessage())
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
