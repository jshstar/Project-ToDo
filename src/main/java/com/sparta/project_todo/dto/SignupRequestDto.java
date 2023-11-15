package com.sparta.project_todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]+$", message = "숫자0~9와 알파벳 소문자로만 조합해서 작성해주세요")
    @Size(min = 4,max = 10, message = "4자 이상 10자 이하로 작성해 주세요")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "숫자0~9와 알파벳 대소문자로만 조합해서 작성해주세요" )
    @Size(min=8, max = 15,message = "8자 이상 15자 이하로 작성해주세요.")
    private String password;

    private boolean admin = false;

    private String adminToken = "";
}

//@Getter
//@Setter
//public class SignupRequestDto {
//    private String username;
//    private String password;
//    private String email;
//    private boolean admin = false;
//    private String adminToken = "";
//}