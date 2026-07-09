package com.billing.waterbilling.exception;

import com.billing.waterbilling.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessException(
            BusinessException ex,
            HttpServletRequest request){

        log.warn(
                "Business Exception [{}] : {}",
                ex.getErrorCode().getCode(),
                ex.getMessage()
        );
        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .code(ex.getErrorCode().getCode())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .path(request.getRequestURI())
                                .build()

                );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> systemException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected Exception", ex);

        return ResponseEntity
                .internalServerError()
                .body(
                        ErrorResponse.builder()
                                .code(ErrorCode.SYSTEM_ERROR.getCode())
                                .message(ErrorCode.SYSTEM_ERROR.getMessage())
                                .timestamp(LocalDateTime.now())
                                .path(request.getRequestURI())
                                .build()
                );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError ->
                        errors.put(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        )
                );

        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .code(ErrorCode.VALIDATION_ERROR.getCode())
                                .message("Validation Failed")
                                .errors(errors)
                                .timestamp(LocalDateTime.now())
                                .path(request.getRequestURI())
                                .build()
                );
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolation(
            jakarta.validation.ConstraintViolationException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getConstraintViolations()
                .forEach(v ->
                        errors.put(
                                v.getPropertyPath().toString(),
                                v.getMessage()
                        ));

        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .code(ErrorCode.VALIDATION_ERROR.getCode())
                                .message("Validation Failed")
                                .errors(errors)
                                .timestamp(LocalDateTime.now())
                                .path(request.getRequestURI())
                                .build()
                );
    }

    @ExceptionHandler(
            org.springframework.orm.ObjectOptimisticLockingFailureException.class
    )
    public ResponseEntity<ErrorResponse> optimisticLock(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(409)
                .body(
                        ErrorResponse.builder()
                                .code(ErrorCode.PAYMENT_CONCURRENT.getCode())
                                .message(ErrorCode.PAYMENT_CONCURRENT.getMessage())
                                .timestamp(LocalDateTime.now())
                                .path(request.getRequestURI())
                                .build()
                );
    }

    @ExceptionHandler({
            org.springframework.dao.CannotAcquireLockException.class,
            org.springframework.dao.PessimisticLockingFailureException.class
    })
    public ResponseEntity<ErrorResponse> lockException(
            Exception ex,
            HttpServletRequest request){

        return ResponseEntity.status(409)
                .body(
                        ErrorResponse.builder()
                                .code(ErrorCode.PAYMENT_CONCURRENT.getCode())
                                .message(ErrorCode.PAYMENT_CONCURRENT.getMessage())
                                .timestamp(LocalDateTime.now())
                                .path(request.getRequestURI())
                                .build()
                );
    }
}
