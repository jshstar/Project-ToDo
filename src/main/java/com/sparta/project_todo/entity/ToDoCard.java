package com.sparta.project_todo.entity;


import com.sparta.project_todo.dto.ToDoRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "todo")
@NoArgsConstructor
public class ToDoCard extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bNum;
    @Column(name = "title")
    @NotBlank
    private String title;
    @Column(name = "contents", length = 1000)
    private String contents;
    @Column(name = "comment", length = 1000)
    private String comment;
    @Column(name = "complete")
    private boolean complete;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
}
