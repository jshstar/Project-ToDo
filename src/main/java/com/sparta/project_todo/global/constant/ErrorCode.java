package com.sparta.project_todo.global.constant;


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

    UNKNOWN_ERROR_NOT_EXIST_REFRESHTOKEN(HttpStatus.NOT_FOUND, "리프레시 토큰이 존재하지 않습니다. 다시 로그인해주세요."),

    UNKNOWN_ERROR_NOT_EXIST_ACCESSTOKEN(HttpStatus.NOT_FOUND, "액세스 토큰이 존재하지 않습니다. 다시 로그인해주세요."),

    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "만료된 토큰입니다. 다시 로그인하세요."),

    ACCESS_DENIED(HttpStatus.BAD_REQUEST, "유효하지 못한 토큰입니다."),

    INVALID_VALUE(HttpStatus.BAD_REQUEST,"잘못된 입력값입니다."),

    // Custom Exception
    INVALID_ACCESS(HttpStatus.FORBIDDEN,"권한이 없습니다."),

    SECURITY(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"),

    INVALID_MADE(HttpStatus.NOT_FOUND,"작성자가 아닙니다"),

    INVALID_USER_CHECK(HttpStatus.NOT_FOUND,"사용자가 아닙니다"),

    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다"),

    INVALID_SUCCESS_PASSWORD(HttpStatus.BAD_REQUEST,"일치하는 비밀번호가 있습니다");


    private final HttpStatus httpStatus;
    private final String message;


}
