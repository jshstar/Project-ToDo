package com.sparta.project_todo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.project_todo.dto.CommentRequestDto;
import com.sparta.project_todo.dto.CommentResponseDto;
import com.sparta.project_todo.dto.ToDoRequestDto;
import com.sparta.project_todo.entity.Comment;
import com.sparta.project_todo.entity.ToDoCard;
import com.sparta.project_todo.entity.User;
import com.sparta.project_todo.entity.UserRoleEnum;
import com.sparta.project_todo.repository.CommentRepository;
import com.sparta.project_todo.repository.ToDoRepository;
import com.sparta.project_todo.security.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

	@Mock
	CommentRepository commentRepository;

	@Mock
	ToDoService toDoService;

	@InjectMocks
	CommentService commentService;

	public User testCreateUser(String num){
		return new User("username"+ num,"123456789", UserRoleEnum.USER);
	}

	public Comment testCreateComment(CommentRequestDto comment, User user, ToDoCard toDoCard) {
		return new Comment(comment.getComment(),user, toDoCard);
	}



	@Nested
	class 댓글_생성테스트{

		@Test
		void 댓글생성전_카드히든_있을때_댓글_추가테스트() throws IllegalAccessException {
			//given
			User user1 = testCreateUser("1");
			ToDoCard toDoCard1 = mock();
			toDoCard1.hiddenFlag(true);

			CommentRequestDto commentRequestDto1 = new CommentRequestDto("comment1");

			given(toDoService.findCard(any())).willReturn(toDoCard1);

			//when
			commentService.createComment(1L,commentRequestDto1, user1);

			//then
			verify(toDoCard1,times(1)).addComment(any(Comment.class));
		}

		@Test
		void 댓글생성전_카드히든_없을때_댓글_추가테스트() throws IllegalAccessException {
			//given

			User user1 = testCreateUser("1");
			ToDoCard toDoCard1 = mock();
			toDoCard1.hiddenFlag(false);
			CommentRequestDto commentRequestDto1 = new CommentRequestDto("comment1");

			given(toDoService.findCard(any())).willReturn(toDoCard1);

			//when
			commentService.createComment(1L,commentRequestDto1, user1);

			//then
			verify(toDoCard1,times(1)).addComment(any(Comment.class));
		}


		@Test
		void 댓글생성_응답테스트() throws IllegalAccessException {
			//given
			ToDoCard toDoCard = mock();
			User user2 = testCreateUser("2");
			CommentRequestDto commentRequestDto = new CommentRequestDto("comment");
			Comment comment = testCreateComment(commentRequestDto,user2, toDoCard);

			given(toDoService.findCard(any())).willReturn(toDoCard);
			given(commentRepository.save(any())).willReturn(comment);
			//when
			CommentResponseDto result = commentService.createComment(1L,commentRequestDto, user2);

			//then
			assertEquals("comment", result.getComment());
		}
	}

	@Test
	void 댓글_업데이트_테스트() throws IllegalAccessException {
		//given
		User user = testCreateUser("1");
		ToDoCard toDoCard = mock();
		CommentRequestDto commentRequestDto = new CommentRequestDto("comment");
		Comment comment = testCreateComment(commentRequestDto,user,toDoCard);
		CommentRequestDto updateCommentRequestDto = new CommentRequestDto("updatecomment");
		comment.setId(1L);

		given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
		//when
		CommentResponseDto result = commentService.updateComment(1L, updateCommentRequestDto, user);

		assertEquals(1L, result.getId());
		assertEquals("updatecomment", result.getComment());
	}

	@Nested
	class 유저_일치_불일치_확인테스트{
		@Test
		void 댓글유저_불일치_확인테스트() {
			//given
			User writerUser = testCreateUser("1");
			User accessUser = testCreateUser("2");
			ToDoCard toDoCard = mock();
			CommentRequestDto commentRequestDto = new CommentRequestDto("comment");
			Comment comment = testCreateComment(commentRequestDto,writerUser,toDoCard);


			//when && then
			Assertions.assertThatCode(() ->
				commentService.matchUsername(comment,accessUser)).isInstanceOf(IllegalAccessException.class);
		}

		@Test
		void 댓글유저_일치_확인테스트(){
			User writerUser = testCreateUser("1");
			User accessUser = testCreateUser("1");
			ToDoCard toDoCard = mock();
			CommentRequestDto commentRequestDto = new CommentRequestDto("comment");
			Comment comment = testCreateComment(commentRequestDto,writerUser,toDoCard);
			//when && then
			Assertions.assertThatCode(() ->
				commentService.matchUsername(comment,accessUser)).doesNotThrowAnyException();

		}

	}

	@Test
	void 댓글_삭제테스트() throws IllegalAccessException {
		//given
		User user = testCreateUser("1");
		ToDoCard toDoCard = mock();
		ReflectionTestUtils.setField(toDoCard,"id",1L);
		Long id =1L;
		CommentRequestDto commentRequestDto = new CommentRequestDto("commnet");
		Comment comment = testCreateComment(commentRequestDto,user,toDoCard);
		ReflectionTestUtils.setField(comment,"id",1L);
		given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

		// when
		Long deleteId = commentService.deleteComment(id, user);

		//then
		then(commentRepository).should().delete(comment);
		Assertions.assertThat(deleteId).isEqualTo(id);
	}

}


