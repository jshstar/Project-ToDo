package com.sparta.project_todo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

    // 예외처리를 위해 만든 클래스지만 잘 모릅니다.
    private final CommonErrorCode errorCode;

}
