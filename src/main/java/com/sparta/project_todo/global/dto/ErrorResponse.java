package com.sparta.project_todo.global.dto;


import lombok.Data;
import lombok.Generated;
import lombok.Getter;

import org.springframework.http.HttpStatus;

import com.sparta.project_todo.global.constant.ErrorCode;

@Data
@Generated
@Getter
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(ErrorCode errorCode){
        this.status = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
    }
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
    }
}
