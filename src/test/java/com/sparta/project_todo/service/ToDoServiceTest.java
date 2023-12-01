package com.sparta.project_todo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.project_todo.dto.CompleteToDoResponseDto;
import com.sparta.project_todo.dto.GetAllToDoResponseDto;
import com.sparta.project_todo.dto.HiddenToDoResponseDto;
import com.sparta.project_todo.dto.SelectToDoResponseDto;
import com.sparta.project_todo.dto.ToDoRequestDto;
import com.sparta.project_todo.dto.ToDoResponseDto;
import com.sparta.project_todo.entity.ToDoCard;
import com.sparta.project_todo.entity.User;
import com.sparta.project_todo.entity.UserRoleEnum;
import com.sparta.project_todo.repository.ToDoRepository;
import com.sparta.project_todo.security.UserDetailsImpl;
@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

	@Mock
	ToDoRepository toDoRepository;

	@InjectMocks
	ToDoService toDoService;

	public UserDetailsImpl testCreateUser(String num){
		return new UserDetailsImpl(new User("username" +num, "123456789", UserRoleEnum.USER));
	}
	public ToDoCard testCreateToDoCard (String num, UserDetailsImpl user){
		ToDoRequestDto requestDto = new ToDoRequestDto("testTitle" + num, "testContents" +num);
		ToDoCard toDoCard = new ToDoCard(requestDto, user.getUser());
		if(Integer.parseInt(num)%2 ==1){
			toDoCard.hiddenFlag(true);
		}
		else {
			toDoCard.hiddenFlag(false);
		}
		return toDoCard;
	}

	public List<ToDoCard> testCreateToDoCardList(ToDoCard card1,ToDoCard card2){
		List<ToDoCard> toDoCardList = new ArrayList<>();
		toDoCardList.add(card1);
		toDoCardList.add(card2);
		return toDoCardList;
	}



	@Test
	void 카드생성_테스트확인() {

		//given
		User user = new User("username", "123456789", UserRoleEnum.USER);
		ToDoRequestDto requestDto = new ToDoRequestDto("testTitle", "testContents");

		ToDoCard toDoCard = new ToDoCard(requestDto, user);

		// when
		// createCard 메서드 호출
		when(toDoRepository.save(any())).thenReturn(toDoCard);
		ToDoResponseDto response = toDoService.createCard(requestDto, user);

		//then
		assertNotNull(response);
		// assertEquals(toDoCard, response.getToDoId());
		assertEquals("username", response.getUsername());
		assertEquals("testTitle", response.getTitle());
		assertEquals("testContents", response.getContents());
		System.out.println("통과");
	}

	@Nested
	class 카드_목록_조회_테스트 {
		@Test
		void 카드_Null값_테스트() {
			//given
			UserDetailsImpl user1 = testCreateUser("1");

			// when && then
			Assertions.assertThatCode(() -> toDoService.getCards(user1)).isInstanceOf(NullPointerException.class);
			System.out.println("통과");
		}

		@Test
		void 카드_목록_조회_이름일치_확인테스트() {
			UserDetailsImpl user1 = testCreateUser("1");
			UserDetailsImpl user2 = testCreateUser("2");
			//given
			ToDoCard toDoCard1 = testCreateToDoCard("1", user1);
			ToDoCard toDoCard2 = testCreateToDoCard("2", user2);

			List<ToDoCard> toDoCardList = testCreateToDoCardList(toDoCard1, toDoCard2);

			// when
			when(toDoRepository.findAllByCompleteOrderByCreatedAtDesc(any())).thenReturn(toDoCardList);
			List<GetAllToDoResponseDto> result = toDoService.getCards(user1);

			// then
			assertEquals("username1", result.get(0).getUsername());
			assertEquals("username2", result.get(1).getUsername());
			assertEquals("testTitle1", result.get(0).getTitle());
			assertEquals("testTitle2", result.get(1).getTitle());
			System.out.println("통과");
		}

	}

	@Nested
	class 카드_제목으로_검색_테스트 {
		@Test
		void 카드_유저_NotNull_그리고_NULL_체크테스트(){
			//given
			UserDetailsImpl user1 = testCreateUser("1");
			UserDetailsImpl user2 = testCreateUser("2");
			String title = "testtitle";

			List<ToDoCard> titleList = testCreateToDoCardList(
			testCreateToDoCard("1",user1),testCreateToDoCard("2",user2));

			given(toDoRepository.titleQuery(any(),any())).willReturn(titleList);
			given(toDoRepository.titleNotUserQuery(any())).willReturn(titleList);

			user2 =null;

			//when
			toDoService.getTitleCards(title, user1);
			toDoService.getTitleCards(title, user2);

			//then
			verify(toDoRepository, times(1)).titleQuery(any(),any());
			verify(toDoRepository, times(1)).titleNotUserQuery(any());
			System.out.println("통과");
		}

		@Test
		void 타이틀_리스트_Null_테스트(){
			//given
			UserDetailsImpl user1 = testCreateUser("1");
			String title = "testtitle";

			//when && then
			Assertions.assertThatCode(() -> toDoService.getTitleCards(title,user1)).isInstanceOf(NullPointerException.class);
			System.out.println("통과");

		}
	}

	@Test
	void 카드_업데이트_성공테스트() throws IllegalAccessException {

		// given
		UserDetailsImpl user1 = testCreateUser("1");
		ToDoCard toDoCard = testCreateToDoCard("1", user1);
		ReflectionTestUtils.setField(toDoCard, "id", 1L);
		ToDoRequestDto toDoRequestDto = new ToDoRequestDto("updatetitle", "updatecontents");


		// when
		given(toDoRepository.findById(any())).willReturn(Optional.of(toDoCard));
		SelectToDoResponseDto result = toDoService.updateCard(1L,toDoRequestDto,user1.getUser());

		// then
		assertEquals("username1", result.getUsername());
		assertEquals("updatetitle", result.getTitle());
		assertEquals("updatecontents", result.getContents());


	}

	@Test
	void 카드_완료_처리테스트() throws IllegalAccessException {
		//given
		UserDetailsImpl user1 = testCreateUser("1");
		ToDoCard toDoCard = testCreateToDoCard("1", user1);
		ReflectionTestUtils.setField(toDoCard, "id", 1L);

		//when
		given(toDoRepository.findById(any())).willReturn(Optional.of(toDoCard));
		CompleteToDoResponseDto result = toDoService.completeCard(1L, user1.getUser());

		//then
		assertTrue(result.isComplete());
	}

	@Test
	void 카드_히든_처리테스트() throws IllegalAccessException {
		//given
		UserDetailsImpl user1 = testCreateUser("1");
		ToDoCard toDoCard = testCreateToDoCard("1", user1);
		ReflectionTestUtils.setField(toDoCard, "id", 1L);

		//when
		given(toDoRepository.findById(any())).willReturn(Optional.of(toDoCard));
		HiddenToDoResponseDto result = toDoService.hiddenCard(1L, user1.getUser());

		//then
		assertTrue(result.isHidden());

	}

	@Nested
	class 유저_일치_불일치_확인테스트{
		@Test
		void 유저_불일치_확인테스트() {
			//given
			UserDetailsImpl user1 = testCreateUser("1");
			UserDetailsImpl user2 = testCreateUser("2");
			ToDoCard toDoCard = testCreateToDoCard("1", user1);


			//when && then
			Assertions.assertThatCode(() ->
				toDoService.matchUsername(toDoCard,user2.getUser())).isInstanceOf(IllegalAccessException.class);
		}

		@Test
		void 유저_일치_확인테스트(){
			UserDetailsImpl cardUser = testCreateUser("1");
			UserDetailsImpl loginUser = testCreateUser("1");
			ToDoCard toDoCard = testCreateToDoCard("1", cardUser);
			//when && then
			Assertions.assertThatCode(() ->
				toDoService.matchUsername(toDoCard,loginUser.getUser())).doesNotThrowAnyException();

		}

	}


}
