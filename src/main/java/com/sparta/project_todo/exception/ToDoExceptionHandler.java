package com.sparta.project_todo.exception;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice
@Generated
public class ToDoExceptionHandler extends ResponseEntityExceptionHandler {

    // 잘못된 유저 접근 에러
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorResponse> handleAccessException(IllegalAccessException e) {
        log.error("IllegalAccessException", e);
        ErrorResponse response =e.getMessage().isEmpty()?
                new ErrorResponse(ErrorCode.INVALID_USER):
                new ErrorResponse(ErrorCode.INVALID_USER.getHttpStatus(),e.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 잘못된 인덱스 접근 에러
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        ErrorResponse response = e.getMessage().isEmpty()?
                new ErrorResponse(ErrorCode.NOT_ACCESS_CARD):
                new ErrorResponse(ErrorCode.NOT_ACCESS_CARD.getHttpStatus(),e.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }

    // null 에러
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointException(NullPointerException e) {
        log.error("NullPointerException",e);
        ErrorResponse response = e.getMessage().isEmpty()?
                new ErrorResponse(ErrorCode.CARD_NOT_FOUND):
                new ErrorResponse(ErrorCode.CARD_NOT_FOUND.getHttpStatus(),e.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }

    // 이외 에러들
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        ErrorResponse response = e.getMessage().isEmpty()?
                new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR):
                new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus(),e.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }



}

