package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Menu;
import com.example.newsfeed.entity.User;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.net.UnknownServiceException;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    Menu menu;
    String text;
    User user;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.menu = comment.getMenu();
        this.text = comment.getText();
        this.user = comment.getUser();
        this.createdAt = LocalDateTime.now();
    }
}
