package com.sparta.project_todo.user.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    // 유저 ID
    @Pattern(regexp = "^[a-z0-9]+$", message = "숫자0~9와 알파벳 소문자로만 조합해서 작성해주세요")
    @Size(min = 4,max = 10, message = "4자 이상 10자 이하로 작성해 주세요")
    private String username;

    // 유저 PW
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "숫자0~9와 알파벳 대소문자로만 조합해서 작성해주세요" )
    @Size(min=8, max = 15,message = "8자 이상 15자 이하로 작성해주세요.")
    private String password;

    // 관리자
    private boolean admin = false;

    // 토큰 변수
    private String adminToken = "";
}
