package com.sparta.project_todo.global.exception;


import lombok.Data;
import lombok.Generated;

import org.springframework.http.HttpStatus;

@Data
@Generated
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    public ErrorResponse(ErrorCode errorCode){
        this.status = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }
}
