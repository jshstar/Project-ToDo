package com.sparta.project_todo.jwt;



import static org.assertj.core.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.project_todo.entity.User;
import com.sparta.project_todo.entity.UserRoleEnum;
import com.sparta.project_todo.security.JwtUtil;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
public class JwtUtilTest {

	@InjectMocks
	private JwtUtil jwtUtil;

	String key = "7Iqk7YyM66W07YOA7L2U65Sp7YG065+9U3ByaW5n6rCV7J2Y7Yqc7YSw7LWc7JuQ67mI7J6F64uI64ukLg==";

	@BeforeEach
	void setup(){
		ReflectionTestUtils.setField(jwtUtil, "secretKey", this.key);
		jwtUtil.init();
	}


	@Test
	void 토큰생성_테스트(){
		//given
		User user = new User("username1","123456789", UserRoleEnum.USER);
		//when
		String token = jwtUtil.createToken(user.getUsername(), user.getRole());
		//then
		assertThat(token).isNotBlank();
	}

	@Test
	void 토큰_BEARER_PREFIX_자르기테스트(){
		//given
		User user = new User("username1","123456789", UserRoleEnum.USER);

		//when
		String token = jwtUtil.createToken(user.getUsername(), user.getRole());
		String bearerToken  = jwtUtil.substringToken(token);

		//when
		assertThat(bearerToken).isNotEqualTo(token);
		assertThat(bearerToken).doesNotContain("Bearer ");

	}
	@Test
	void 토큰_검증테스트(){
		//given
		User user = new User("username1","123456789", UserRoleEnum.USER);//when
		//when
		String token = jwtUtil.createToken(user.getUsername(), user.getRole());
		String bearerToken  = jwtUtil.substringToken(token);
		boolean flag = jwtUtil.validateToken(bearerToken);
		//then
		assertThat(flag).isEqualTo(true);

	}

	@Test
	void 토큰_만료_검증테스트(CapturedOutput output) throws Exception {
		//given
		User user = new User("username1","123456789", UserRoleEnum.USER);
		ReflectionTestUtils.setField(jwtUtil,"TOKEN_TIME",500L);
		//when
		String token = jwtUtil.createToken(user.getUsername(), user.getRole());
		String bearerToken  = jwtUtil.substringToken(token);
		Thread.sleep(1000L);
		jwtUtil.validateToken(bearerToken);
		//then
		assertThat(output.getOut()).contains("Expired");
	}

	@Test
	void 토큰_유효_검증테스트(CapturedOutput output) {
		//given
		User user = new User("username1","123456789", UserRoleEnum.USER);
		//when
		jwtUtil.validateToken(user.getUsername());
		//then
		assertThat(output.getOut()).contains("Invalid");
	}

	@Test
	void 토큰_빈토큰_검증테스트(CapturedOutput output) {
		//when
		jwtUtil.validateToken("");
		//then
		assertThat(output.getOut()).contains("claims");
	}
}
