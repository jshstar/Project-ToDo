package com.sparta.project_todo.user.dto;

import lombok.Getter;

@Getter
public class VerificationRequestDto {
	private String email;

	private String authCode;
}
