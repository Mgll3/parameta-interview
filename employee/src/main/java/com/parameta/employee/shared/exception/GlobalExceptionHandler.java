package com.parameta.employee.shared.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.parameta.employee.shared.utils.Constants;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> handleExceptionDRY(Exception e, String errorCode, HttpStatus status) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(errorCode, e.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(SoapFaultClientException.class)
    public ResponseEntity<ErrorResponse> handleSoapFaultClientException(SoapFaultClientException e) {
        log.error(e.getMessage(), e);
        return handleExceptionDRY(e, Constants.ERROR_SOAP_FAULT_CLIENT, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error(Constants.LOG_VALIDATION_ERROR, ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, String>> handleWebExchangeBindException(WebExchangeBindException ex) {
        log.error(Constants.LOG_WEB_EXCHANGE_BIND_EXCEPTION, ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return handleExceptionDRY(e, Constants.ERROR_INVALID_ARGUMENT, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        log.error(e.getMessage(), e);
        return handleExceptionDRY(e, Constants.ERROR_INTERNAL_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public class ErrorResponse {
        private String errorCode;
        private String message;
    }
}
