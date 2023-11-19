package com.sparta.project_todo.entity;


import com.sparta.project_todo.dto.ToDoRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private Long id; // 카드 번호

    @Column(name = "title")
    @NotBlank
    private String title; // 카드 제목

    @Column(name = "contents", length = 1000)
    private String contents; // 카드 내용

    @Column(name = "complete")
    private boolean complete; // 완료 여부

    @Column(name = "hidden")
    private boolean hidden; // 비공개 여부

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 유저 정보

    @OneToMany(mappedBy = "toDoCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments; // 댓글 내용이 담긴 목록

    // 카드 작성
    public ToDoCard(ToDoRequestDto cardRequestDto, User user) {
        this.title = cardRequestDto.getTitle();
        this.contents = cardRequestDto.getContents();
        this.user = user;
    }

    // 카드 수정
    public void update(ToDoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    // 카드 할일 완료처리
    public void complete(boolean flag) {
        this.complete = flag;
    }

    // 카드 비공개처리
    public void hiddenFlag(boolean flag) {
        this.hidden = flag;
    }

    // 댓글 추가
    public void addComment(Comment comment) {
        comment.matchToDoCard(this);
        this.comments.add(comment);
    }


}
