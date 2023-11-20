package com.sparta.project_todo.service;


import com.sparta.project_todo.dto.CommentRequestDto;
import com.sparta.project_todo.dto.CommentResponseDto;
import com.sparta.project_todo.entity.Comment;
import com.sparta.project_todo.entity.ToDoCard;
import com.sparta.project_todo.entity.User;
import com.sparta.project_todo.repository.CommentRepository;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository; // 댓글 Repository

    private final ToDoService toDoService; // 카드 Repository

    public CommentService(CommentRepository commentRepository, ToDoService toDoService) {
        this.commentRepository = commentRepository;
        this.toDoService = toDoService;
    }

    @Transactional
    public CommentResponseDto createComment(Long id ,CommentRequestDto commentRequestDto, User user) throws IllegalAccessException {
        ToDoCard card = toDoService.findCard(id); // 댓글 달고자 하는 카드 정보 탐색 및 영속성 상태로 저장
        Comment comment = new Comment(commentRequestDto.getComment(),user, card);

        if(card.isHidden()) // 카드 비공개 check
        {
            if(toDoService.matchUsername(card, comment.getUser()))
                card.addComment(comment);
        }
        else card.addComment(comment);

        commentRepository.save(comment); // 댓글 DB 저장

        return new CommentResponseDto(comment); // 댓글 정보 반환
    }


    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, User user) throws IllegalAccessException{

        Comment comment = findComment(id); // 수정할 댓글의 id 탐색 및 영속성 상태로 저장

        if(matchUsername(comment,user)) { // 입력 돼있는 유저의 정보와 수정할 유저의 정보 일치 여부 확인
            comment.updateComment(commentRequestDto.getComment()); // 댓글 수정 업데이트
            return new CommentResponseDto(comment); // 수정된 댓글 정보 반환
        }
        else throw new IllegalAccessException("본인의 댓글이 아닙니다."); // 일치 하지 않으면 예외 처리
    }

    @Transactional
    public Long deleteComment(Long id, User user) throws IllegalAccessException{
        Comment comment = findComment(id); // 삭제할 댓글 정보 확인 및 영속성 상태로 저장

        if(matchUsername(comment,user)) // 입력 돼있는 유저의 정보와 수정할 유저의 정보 일치 여부 확인
        {
            commentRepository.delete(comment); // DB에 저장 돼있는 댓글 삭제
            return id; // 삭제한 id반환
        }
        else throw new IllegalAccessException("본인의 댓글이 아닙니다."); // 일치 하지 않으면 예외 처리

    }

    // 댓글 정보 탐색
    private Comment findComment(Long id){
        return commentRepository.findById(id).orElseThrow(()-> // DB에서 id 정보 탐색후 있으면 반환 없으면 예외처리
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }

    // 댓글에 있는 유저정보와 접근하는 유저의 이름 일치 확인
    private boolean matchUsername(Comment comment, User user){
        return comment.getUser().getUsername().equals(user.getUsername());
    }

}
