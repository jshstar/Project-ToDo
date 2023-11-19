package com.sparta.project_todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 저장 번호

    @Column(name = "comment", length = 1000)
    private String comment; // 내용

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 유저 정보

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private ToDoCard toDoCard; // 카드 정보

    public Comment(String comment, User user, ToDoCard toDoCard)
    {
        this.comment = comment;
        this.user = user;
        this.toDoCard = toDoCard;
    }

    public void matchToDoCard(ToDoCard toDoCard){
        this.toDoCard = toDoCard;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
