package com.sparta.project_todo.service;


import com.sparta.project_todo.dto.CommentRequestDto;
import com.sparta.project_todo.dto.CommentResponseDto;
import com.sparta.project_todo.entity.Comment;
import com.sparta.project_todo.entity.ToDoCard;
import com.sparta.project_todo.entity.User;
import com.sparta.project_todo.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final ToDoService toDoService;

    public CommentService(CommentRepository commentRepository, ToDoService toDoService) {
        this.commentRepository = commentRepository;
        this.toDoService = toDoService;
    }

    @Transactional
    public CommentResponseDto createComment(Long id ,CommentRequestDto commentRequestDto, User user)
    {
        ToDoCard card = toDoService.findCard(id);
        Comment comment = new Comment(commentRequestDto.getComment(),user, card);
        card.addComment(comment);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }


    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, User user) throws IllegalAccessException{
        Comment comment = findComment(id);
        if(comment.getUser().getUsername().equals(user.getUsername()))
        {
            comment.updateComment(commentRequestDto.getComment());
            return new CommentResponseDto(comment);
        }
        else throw new IllegalAccessException("본인의 댓글이 아닙니다.");
    }

    @Transactional
    public Long deleteComment(Long id, User user) throws IllegalAccessException{
        Comment comment = findComment(id);
        if(comment.getUser().getUsername().equals(user.getUsername()))
        {
            commentRepository.delete(comment);
            return id;
        }
        else throw new IllegalAccessException("본인의 댓글이 아닙니다.");

    }

    private Comment findComment(Long cNum){
        return commentRepository.findById(cNum).orElseThrow(()->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }

}
