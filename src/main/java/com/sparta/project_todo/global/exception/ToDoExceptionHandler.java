package com.sparta.project_todo.global.exception;

import static com.sparta.project_todo.global.constant.ErrorCode.*;

import java.util.HashMap;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sparta.project_todo.global.constant.ErrorCode;
import com.sparta.project_todo.global.dto.ErrorResponse;

@Slf4j
@RestControllerAdvice
@Generated
public class ToDoExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("handleMethodArgumentNotValidException", ex);
        BindingResult bindingResult = ex.getBindingResult();
        HashMap<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors()
            .forEach(error -> errors.put(((FieldError)error).getField(), error.getDefaultMessage()));

        return ResponseEntity.status(INVALID_VALUE.getHttpStatus()).body(
            new ErrorResponse(INVALID_VALUE.getHttpStatus().value(), INVALID_VALUE.getMessage())
        );
    }
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ErrorResponse(INVALID_VALUE.getHttpStatus().value(),
                INVALID_VALUE.getMessage() + "누락된 파라미터" + ex.getParameterName())
        );
    }


    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorResponse> illegalAccessExceptionExceptionHandler(IllegalAccessException ex){
        log.error(ex.getMessage());
        ErrorResponse response = ex.getMessage().isEmpty()?new ErrorResponse(INVALID_ACCESS)
            :new ErrorResponse(INVALID_ACCESS.getHttpStatus().value(),ex.getMessage());
        return ResponseEntity.status(INVALID_ACCESS.getHttpStatus().value()).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        ErrorResponse response = ex.getMessage().isEmpty()?
             new ErrorResponse(INVALID_VALUE)
            :new ErrorResponse(INVALID_VALUE.getHttpStatus().value(),
            ex.getMessage());
        return ResponseEntity.status(INVALID_VALUE.getHttpStatus().value()).body(response);
    }


    // 이외 에러들
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        log.error("RuntimeException", e);
        ErrorResponse response = e.getMessage().isEmpty()?
            new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR):
            new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value(),e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleCustomException(ApiException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        String message = ex.getMessage();
        if (!StringUtils.hasText(message)) {
            message = errorCode.getMessage();
        }
        log.error("handleCustomException throw CustomException : {}", errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(
            new ErrorResponse(errorCode.getHttpStatus().value(), message)
        );
    }


}

