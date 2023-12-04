package com.sparta.project_todo.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.project_todo.config.WebSecurityConfig;
import com.sparta.project_todo.dto.CommentRequestDto;
import com.sparta.project_todo.dto.CommentResponseDto;
import com.sparta.project_todo.dto.ToDoRequestDto;
import com.sparta.project_todo.entity.Comment;
import com.sparta.project_todo.entity.ToDoCard;
import com.sparta.project_todo.entity.User;
import com.sparta.project_todo.entity.UserRoleEnum;
import com.sparta.project_todo.security.MockSpringSecurityFilter;
import com.sparta.project_todo.security.UserDetailsImpl;
import com.sparta.project_todo.service.CommentService;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(
	controllers = {CommentController.class },
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = WebSecurityConfig.class
		)
	}
)
class CommentControllerTest {

	private MockMvc mvc;

	private Principal mockPrincipal;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	CommentService commentService;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
			.apply(springSecurity(new MockSpringSecurityFilter()))
			.alwaysDo(print())
			.build();
	}

	private User mockUserSetup(String num) {
		// Mock 테스트 유져 생성
		String username = "testuser" + num;
		String password = "123456789";
		UserRoleEnum role = UserRoleEnum.USER;
		User testUser = new User(username, password, role);
		UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
		mockPrincipal =
			new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
		return testUser;
}

	@Test
	void 댓글생성_성공테스트() throws Exception {
		//given
		User user = mockUserSetup("1");
		ToDoCard toDoCard = new ToDoCard(new ToDoRequestDto("title","contents"),user);
		ReflectionTestUtils.setField(toDoCard,"id",1L);
		CommentRequestDto commentRequestDto = new CommentRequestDto("comment");

		Comment comment = new Comment(commentRequestDto.getComment(),user,toDoCard);
		ReflectionTestUtils.setField(comment,"id",1L);
		CommentResponseDto result = new CommentResponseDto(comment);

		given(commentService.createComment(any(Long.class),any(CommentRequestDto.class),any(User.class))).willReturn(result);
		String commentInfo = objectMapper.writeValueAsString(commentRequestDto);

		//when
		mvc.perform(post("/api/todo/comment/{id}",1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(commentInfo)
				.accept(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
			)
			//then
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.id").value(result.getId()))
			.andExpect(jsonPath("$.comment").value(result.getComment()));
	}

	@Test
	void 댓글_업데이트_성공테스트() throws Exception {
		//given
		User user = mockUserSetup("1");
		ToDoRequestDto toDoRequestDto = new ToDoRequestDto("title", "contents");
		ToDoCard toDoCard = new ToDoCard(toDoRequestDto, user);
		ReflectionTestUtils.setField(toDoCard,"id",1L);

		CommentRequestDto updateCommentRequestDto= new CommentRequestDto("updatecommnet");
		Comment resultComment = new Comment(updateCommentRequestDto.getComment(),user,toDoCard);
		ReflectionTestUtils.setField(resultComment,"id",1L);
		CommentResponseDto result = new CommentResponseDto(resultComment);

		given(commentService.updateComment(any(Long.class),any(CommentRequestDto.class),any(User.class)))
			.willReturn(result);

		String commentInfo = objectMapper.writeValueAsString(updateCommentRequestDto);

		//when
		mvc.perform(put("/api/comment/{id}",1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(commentInfo)
				.accept(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
			)
			//then
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.id").value(result.getId()))
			.andExpect(jsonPath("$.comment").value(result.getComment()));

	}


}
