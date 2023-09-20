package com.prgrms.wadiz.global.util.exception;

import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseTemplate> defaultException() {
        return new ResponseEntity<>(
                ResponseFactory.getFailResult(
                        ErrorCode.UNKNOWN.getCode(),
                        ErrorCode.UNKNOWN.getErrorMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ResponseTemplate> handleCustomerException(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();

        return new ResponseEntity<>(
                ResponseFactory.getFailResult(
                        errorCode.getCode(),
                        errorCode.getErrorMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

