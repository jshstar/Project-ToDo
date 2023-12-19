package com.sparta.project_todo.global.exception;


import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@Generated
public enum ErrorCode {
    // 예외처리를 위해 만든 클래스지만 잘 모릅니다.

    // System Exception
    INVALID_USER(HttpStatus.BAD_REQUEST,"일치하는 유저정보가 없습니다."),

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "일치하는 토큰이 없습니다."),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"잘못된 입력값입니다."),

    CARD_CREATE_NOT(HttpStatus.BAD_REQUEST,"Card를 생성할 수 없습니다."),

    NOT_ACCESS_CARD(HttpStatus.BAD_REQUEST,"해당하는 카드에 접근할 수 없습니다."),

    CARD_NOT_FOUND(HttpStatus.NOT_FOUND,"해당하는 Card를 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),

    // Custom Exception
    SECURITY(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"),;

    private final HttpStatus httpStatus;
    private final String message;


}
