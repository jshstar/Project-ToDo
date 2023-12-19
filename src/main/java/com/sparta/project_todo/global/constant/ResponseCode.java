package com.sparta.project_todo.global.constant;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

	SUCCESS_REISSUANCETOKEN(OK, "토큰이 재발급되었습니다."),
	SUCCESS_SIGNUP(OK, "회원가입이 성공하였습니다,"),
	SUCCESS_LOGOUT(OK, "로그아웃이 성공하였습니다."),
	SUCCESS_EDITPROFILE(OK, "내정보 수정이 성공하였습니다."),

	SUCCESS_CREATE(CREATED,"카드 생성 성공"),
	SUCCESS_GET_CARD(OK, "카드 조회 성공"),
	SUCCESS_GET_SELECT_CARD(OK, "카드 선택 조회 성공"),
	SUCCESS_GET_TITLE_CARD(OK, "카트 제목 조회 성공"),
	SUCCESS_UPDATE_CARD(OK, "카드 업데이트 성공"),
	SUCCESS_COMPLETE(OK, "카드 완료처리 완료"),
	SUCCESS_HIDDEN(OK,"카드 히튼처리 완료"),
	SUCCESS_DELETE(OK, "카드 삭제 완료");

	private final HttpStatus httpStatus;
	private final String message;
}
