package com.sparta.project_todo.global.exception;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Generated
public class RestApiException extends RuntimeException {

    private final ErrorCode errorCode;


}