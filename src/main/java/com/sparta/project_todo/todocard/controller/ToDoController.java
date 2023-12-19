package com.sparta.project_todo.todocard.controller;


import static com.sparta.project_todo.global.constant.ResponseCode.*;

import com.sparta.project_todo.global.dto.SuccessResponse;
import com.sparta.project_todo.todocard.dto.CompleteToDoResponseDto;
import com.sparta.project_todo.todocard.dto.GetAllToDoResponseDto;
import com.sparta.project_todo.todocard.dto.HiddenToDoResponseDto;
import com.sparta.project_todo.todocard.dto.SelectToDoResponseDto;
import com.sparta.project_todo.todocard.dto.ToDoRequestDto;
import com.sparta.project_todo.security.UserDetailsImpl;
import com.sparta.project_todo.todocard.dto.ToDoResponseDto;
import com.sparta.project_todo.todocard.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService boardService) {
        this.toDoService = boardService;
    }

    // 게시글 작성
    @PostMapping("/todo")
    public ResponseEntity<SuccessResponse> createCard(@RequestBody ToDoRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ToDoResponseDto toDoResponseDto = toDoService.createCard(requestDto, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_CREATE.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_CREATE,toDoResponseDto));
    }

    // 게시글 목록 조회
    @GetMapping("/todo")
    public ResponseEntity<SuccessResponse> getCards(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<GetAllToDoResponseDto> responseDto = toDoService.getCards(userDetails);

        return ResponseEntity.status(SUCCESS_GET_CARD.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_GET_CARD, responseDto));
    }

    // 선택된 카드 조회
    @GetMapping("/todo/{id}")
    public ResponseEntity<SuccessResponse> selectGetCard(@PathVariable Long id) {
        SelectToDoResponseDto selectToDoResponseDto = toDoService.selectGetCards(id);
        return ResponseEntity.status(SUCCESS_GET_SELECT_CARD.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_GET_SELECT_CARD, selectToDoResponseDto));
    }

    // 제목으로 카드목록 조회
    @GetMapping("/todo/title")
    public ResponseEntity<SuccessResponse> getTitleCard(@RequestParam(value = "title") String title,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<GetAllToDoResponseDto> getAllToDoResponseDto = toDoService.getTitleCards(title, userDetails);
        return ResponseEntity.status(SUCCESS_GET_TITLE_CARD.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_GET_TITLE_CARD, getAllToDoResponseDto));
    }

    // 카드 업데이트
    @PutMapping("/todo/{id}")
    public ResponseEntity<SuccessResponse> updateCard(@PathVariable Long id,
                                        @RequestBody ToDoRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        SelectToDoResponseDto selectToDoResponseDto = toDoService.updateCard(id, requestDto, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_UPDATE_CARD.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_UPDATE_CARD, selectToDoResponseDto));
    }

    // 카드 완료 업데이트
    @PutMapping("/todo/{id}/complete")
    public ResponseEntity<SuccessResponse> completeCard(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        CompleteToDoResponseDto completeToDoResponseDto = toDoService.completeCard(id, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_COMPLETE.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_COMPLETE, completeToDoResponseDto));
    }

    // 개시글 비공개 처리
    @PutMapping("/todo/{id}/hidden")
    public ResponseEntity<SuccessResponse> hidden(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException{
        HiddenToDoResponseDto hiddenToDoResponseDto = toDoService.hiddenCard(id, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_HIDDEN.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_HIDDEN, hiddenToDoResponseDto));
    }


    // 카드 삭제
    @DeleteMapping("/todo/{id}")
    public ResponseEntity<SuccessResponse> deleteCard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException{
        Long deleteId = toDoService.deleteCard(id, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_DELETE.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_DELETE, userDetails.getUser().getUsername()
                + "님의 " + deleteId + "번째 글을 삭제 완료했습니다."));
    }


}
