package com.sparta.project_todo.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.project_todo.email.service.EmailService;
import com.sparta.project_todo.security.JwtUtil;
import com.sparta.project_todo.user.dto.SignupRequestDto;
import com.sparta.project_todo.user.dto.VerificationRequestDto;
import com.sparta.project_todo.user.entity.User;
import com.sparta.project_todo.user.entity.UserRoleEnum;
import com.sparta.project_todo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    public void prepareSignup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        String password = passwordEncoder.encode(requestDto.getPassword());
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;

        // 사용자 임시 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }

    public Boolean verifyAuthCode(VerificationRequestDto verificationRequestDto){
        String email = verificationRequestDto.getEmail();
        String code = verificationRequestDto.getAuthCode();
        Boolean verifyCheckFlag  =  emailService.verifyEmailCode(email,code);
        verifyCheck(verifyCheckFlag, verificationRequestDto.getEmail());
        return verifyCheckFlag;
    }

    public void verifyCheck(Boolean verifyCheckFlag , String email){
        if(verifyCheckFlag == null){
            userRepository.failVerify(email);
        } else if(verifyCheckFlag){
            userRepository.successVerify(email);
        }
    }

}
