package com.sparta.project_todo.user.controller;

import static com.sparta.project_todo.global.constant.ErrorCode.*;
import static com.sparta.project_todo.global.constant.ResponseCode.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.project_todo.email.entity.EmailMessage;
import com.sparta.project_todo.email.service.EmailService;
import com.sparta.project_todo.global.dto.ErrorResponse;
import com.sparta.project_todo.global.dto.SuccessResponse;
import com.sparta.project_todo.user.dto.SignupRequestDto;
import com.sparta.project_todo.user.dto.VerificationRequestDto;
import com.sparta.project_todo.user.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ToDoUserController {

    private final UserService userService;
    private final EmailService emailService;

    public ToDoUserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        log.info(requestDto.toString());
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.ok("회원 가입 실패");
        }

        userService.prepareSignup(requestDto);

        EmailMessage emailMessage = EmailMessage.builder()
            .to(requestDto.getEmail())
            .subject("[SAVIEW] 이메일 인증을 위한 인증 코드 발송")
            .build();
        emailService.sendMail(emailMessage, "email");

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(new SuccessResponse(HttpStatus.OK.value(), "이메일을 통해 인증코드를 보냈습니다 5분안에 인증을 진행해 주세요"));
    }

    @PostMapping("/user/email-code/verification")
    public ResponseEntity<?> verifyAuthCode(@RequestBody VerificationRequestDto verificationRequestDto){
        Boolean verifyCheckFlag = userService.verifyAuthCode(verificationRequestDto);
        if( verifyCheckFlag == null){
            return ResponseEntity.status(INVALID_SIGNUP.getHttpStatus().value())
                .body(new ErrorResponse(INVALID_SIGNUP.getHttpStatus().value(), INVALID_SIGNUP.getMessage() ));
        } else if(verifyCheckFlag) {
            return ResponseEntity.status(SUCCESS_SIGNUP.getHttpStatus().value())
                .body(new SuccessResponse(SUCCESS_SIGNUP));
        } else{
            return ResponseEntity.status(INVALID_SIGNUP.getHttpStatus().value())
                .body(new ErrorResponse(INVALID_SIGNUP.getHttpStatus().value(), INVALID_SIGNUP.getMessage()+ "다시 인증해주세요"));
        }
    }

}
