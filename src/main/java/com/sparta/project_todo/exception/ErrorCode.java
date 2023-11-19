package com.sparta.project_todo.exception;

import org.springframework.http.HttpStatus;


public interface ErrorCode {

    // 예외처리를 위해 만든 클래스지만 잘 모릅니다.
    String name();
    HttpStatus getHttpStatus();
    String getMessage();

}
