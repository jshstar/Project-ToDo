package com.sparta.project_todo.comment.controller;

import static com.sparta.project_todo.global.constant.ResponseCode.*;

import com.sparta.project_todo.comment.dto.CommentRequestDto;
import com.sparta.project_todo.comment.dto.CommentResponseDto;
import com.sparta.project_todo.global.constant.ResponseCode;
import com.sparta.project_todo.global.dto.SuccessResponse;
import com.sparta.project_todo.security.UserDetailsImpl;
import com.sparta.project_todo.comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성
    @PostMapping("/todo/comment/{id}")
    public ResponseEntity<SuccessResponse> createComment(@PathVariable(name = "id") Long id,
                                           @Valid @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException{
        CommentResponseDto commentResponseDto = commentService.createComment(id, commentRequestDto, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_CREATE_COMMENT.getHttpStatus().value()).body(
            new SuccessResponse(SUCCESS_CREATE_COMMENT, commentResponseDto));

    }

    // 댓글 업데이트
    @PutMapping("/comment/{id}")
    public ResponseEntity<SuccessResponse> updateComment(@PathVariable(name = "id") Long id,
                                           @Valid @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        CommentResponseDto commentResponseDto = commentService.updateComment(id, commentRequestDto, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_UPDATE_COMMENT.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_UPDATE_COMMENT, commentResponseDto));

    }

    // 댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<SuccessResponse> deleteComment(@PathVariable(name = "id") Long id,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        Long deleteId = commentService.deleteComment(id, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_DELETE_COMMENT.getHttpStatus().value())
            .body(new SuccessResponse(SUCCESS_DELETE_COMMENT, "요청하신" + deleteId+ "번째 글이 삭제되었습니다."));
    }


}
