package com.example.newsfeed.entity;

import com.example.newsfeed.dto.CommentRequestDto;
import com.example.newsfeed.dto.CommentResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "comment")
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String text;
    @Column
    private String userNickname;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // 게시글 객체
    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonIgnore
    private Menu menu;


    public Comment(CommentRequestDto commentRequestDto, User user, Menu menu) {
        this.text = commentRequestDto.getText();
        this.createdAt = LocalDateTime.now();
        this.user = user;
        this.menu = menu;
        this.userNickname = user.getNickname();
    }
    public void updateComment(CommentRequestDto commentRequestDto){
        this.text = commentRequestDto.getText();
        this.modifiedAt = LocalDateTime.now();
    }
}
