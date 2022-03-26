package com.study.test.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> customExceptionHandling(CustomException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .build(),
                HttpStatus.valueOf(errorCode.getStatus())
        );
    }

    //@Valid에서 나온 error를 처리해주는 역할
    @ExceptionHandler(BindException.class)
    public ResponseEntity<BindErrorResponse> bindExceptionHandling(BindException e) {
        List<String> errorsList = new ArrayList<>();

        for (FieldError error : e.getFieldErrors()) {
            errorsList.add(error.getDefaultMessage());
        }
        BindErrorResponse errorResponse = BindErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .messages(errorsList)
                .build();

        return new ResponseEntity<BindErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
