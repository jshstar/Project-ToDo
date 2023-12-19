package com.sparta.project_todo.repository;



import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.sparta.project_todo.todocard.dto.ToDoRequestDto;
import com.sparta.project_todo.todocard.entity.ToDoCard;
import com.sparta.project_todo.todocard.repository.ToDoRepository;
import com.sparta.project_todo.user.entity.User;
import com.sparta.project_todo.user.entity.UserRoleEnum;
import com.sparta.project_todo.user.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ToDoRepositoryTest {
	@Autowired
	ToDoRepository toDoRepository;

	@Autowired
	UserRepository userRepository;

	@MockBean
	PasswordEncoder passwordEncoder;

	User user1;

	User user2;

	ToDoCard toDoCard1;

	ToDoCard toDoCard2;

	ToDoCard toDoCard3;


	@BeforeEach
	void init() {
		//given
		User user1 = new User("username1", "123456789", UserRoleEnum.USER);
		this.user1 = userRepository.save(user1);
		User user2 = new User("username2", "123456789", UserRoleEnum.USER);
		this.user2 = userRepository.save(user2);
		ToDoCard toDoCard1 = new ToDoCard(new ToDoRequestDto("test1","content1"),this.user1);
		toDoCard1.hiddenFlag(false);
		toDoCard1.complete(false);
		ToDoCard toDoCard2 = new ToDoCard(new ToDoRequestDto("test2","content2"),this.user2);
		toDoCard2.hiddenFlag(true);
		toDoCard2.complete(false);
		ToDoCard toDoCard3 = new ToDoCard(new ToDoRequestDto("update","content3"),this.user1);
		toDoCard3.hiddenFlag(false);
		toDoCard3.complete(true);
		this.toDoCard1 = toDoRepository.save(toDoCard1);
		this.toDoCard2 = toDoRepository.save(toDoCard2);
		this.toDoCard3 = toDoRepository.save(toDoCard3);
	}

	@Test
	void 완료카드_안돼있는_검색테스트(){

		//when
		List<ToDoCard> findCompleteCard = toDoRepository.findAllByCompleteOrderByCreatedAtDesc(false);

		//then
		assertThat(findCompleteCard).isNotNull();
		assertThat(findCompleteCard.size()).isEqualTo(2);
		assertThat(findCompleteCard.get(0)).isEqualTo(toDoCard2);
		assertThat(findCompleteCard.get(1)).isEqualTo(toDoCard1);
	}

	@Test
	void 완료카드_돼있는_검색테스트(){
		//when
		List<ToDoCard> findCompleteCard = toDoRepository.findAllByCompleteOrderByCreatedAtDesc(true);

		//then
		assertThat(findCompleteCard).isNotNull();
		assertThat(findCompleteCard.size()).isEqualTo(1);
		assertThat(findCompleteCard.get(0)).isEqualTo(toDoCard3);
	}

	@Test
	void 유저로그인제목_검색테스트(){
		//when
		List<ToDoCard> findCompleteCard = toDoRepository.titleQuery("test", user2.getId());

		//then
		assertThat(findCompleteCard).isNotNull();
		assertThat(findCompleteCard.size()).isEqualTo(2);
		assertThat(findCompleteCard.get(0)).isEqualTo(toDoCard1);
		assertThat(findCompleteCard.get(1)).isEqualTo(toDoCard2);
	}

	@Test
	void 비로그인제목_검색테스트(){
		//when
		List<ToDoCard> findCompleteCard = toDoRepository.titleNotUserQuery("test");

		//then
		assertThat(findCompleteCard).isNotNull();
		assertThat(findCompleteCard.size()).isEqualTo(1);
		assertThat(findCompleteCard.get(0)).isEqualTo(toDoCard1);

	}




}
