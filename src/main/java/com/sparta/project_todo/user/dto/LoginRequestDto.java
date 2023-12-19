package com.sparta.project_todo.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    private String username; // ID

    private String password; // PW
}
