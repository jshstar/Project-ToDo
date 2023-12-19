package com.sparta.project_todo.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.sparta.project_todo.user.entity.User;
import com.sparta.project_todo.user.entity.UserRoleEnum;
import com.sparta.project_todo.user.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@MockBean
	PasswordEncoder passwordEncoder;

	User user1;

	User user2;

	@Test
	void 유저검색_테스트(){
		//given
		User user1 = new User("username1", "123456789", UserRoleEnum.USER);
		this.user1 = userRepository.save(user1);
		User user2 = new User("username2", "123456789", UserRoleEnum.USER);
		this.user2 = userRepository.save(user2);
		//when
		Optional<User> findUser = userRepository.findByUsername(this.user1.getUsername());
		//then
		assertThat(findUser).isNotEmpty();
		assertThat(findUser.orElseThrow().getUsername()).isEqualTo(this.user1.getUsername());

	}
}
