package com.sparta.project_todo.entity;


import com.sparta.project_todo.dto.CommentRequestDto;
import com.sparta.project_todo.dto.ToDoRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "todo")
@NoArgsConstructor
public class ToDoCard extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "contents", length = 1000)
    private String contents;

    @Column(name = "complete")
    private boolean complete;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "toDoCard", cascade =CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;

    // 게시글 작성
    public ToDoCard(ToDoRequestDto cardRequestDto, User user) {
        this.title = cardRequestDto.getTitle();
        this.contents = cardRequestDto.getContents();
        this.user =user;
    }

    public void update(ToDoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void complete(boolean flag){
        this.complete = flag;
    }

    public void addComment(Comment comment) {
        comment.matchToDoCard(this);
        this.comments.add(comment);
    }



}
