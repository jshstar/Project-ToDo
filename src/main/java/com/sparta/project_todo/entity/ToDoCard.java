package com.sparta.project_todo.entity;


import com.sparta.project_todo.dto.ToDoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "board")
@NoArgsConstructor
public class ToDoCard extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bNum;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "userName", nullable = false, length = 20)
    private String userName;
    @Column(name = "contents", length = 500)
    private String contents;
    @Column(name = "pw", nullable = false, length = 20)
    private String pw;

    // 게시글 작성
    public ToDoCard(ToDoRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
    }

    public void update(ToDoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
