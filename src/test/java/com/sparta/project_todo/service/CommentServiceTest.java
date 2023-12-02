package com.sparta.project_todo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.sparta.project_todo.dto.CommentRequestDto;
import com.sparta.project_todo.dto.CommentResponseDto;
import com.sparta.project_todo.dto.ToDoRequestDto;
import com.sparta.project_todo.entity.Comment;
import com.sparta.project_todo.entity.ToDoCard;
import com.sparta.project_todo.entity.User;
import com.sparta.project_todo.entity.UserRoleEnum;
import com.sparta.project_todo.repository.CommentRepository;
import com.sparta.project_todo.repository.ToDoRepository;

class CommentServiceTest {

	@Mock
	CommentRepository commentRepository;
	@Mock
	ToDoRepository toDoRepository;

	@InjectMocks
	ToDoService toDoService;

	@InjectMocks
	CommentService commentService;

	public User testcreateUser(String num){
		return new User("username"+ num,"123456789", UserRoleEnum.USER);
	}
	public ToDoCard testCreateToDoCard (String num, User user){
		ToDoRequestDto requestDto = new ToDoRequestDto("testTitle" + num, "testContents" +num);
		ToDoCard toDoCard = new ToDoCard(requestDto, user);
		if(Integer.parseInt(num)%2 ==1){
			toDoCard.hiddenFlag(true);
		}
		else {
			toDoCard.hiddenFlag(false);
		}
		return toDoCard;
	}

	public Comment testcreateComment(String comment, User user, ToDoCard toDoCard) {
		return new Comment(comment,user, toDoCard);
	}



	@Nested
	class 댓글_생성테스트{
		@Test
		void 댓글생성_테스트() throws IllegalAccessException {
			//given
			User user1 = testcreateUser("1");
			ToDoCard toDoCard = testCreateToDoCard("1", user1);
			User user2 = testcreateUser("2");
			CommentRequestDto commentRequestDto = new CommentRequestDto("comment");
			Comment comment = testcreateComment(commentRequestDto.getComment(),user2, toDoCard);
			given(toDoService.findCard(1L)).willReturn(Optional.of(toDoCard));
			given(commentRepository.save(comment)).willReturn(comment);

			//when
			CommentResponseDto result = commentService.createComment(1L,commentRequestDto, user2);

			//then
			assertEquals("comment", result.getComment());

		}


	}
}
