package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Menu;
import com.example.newsfeed.entity.User;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.net.UnknownServiceException;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto extends CommonResponseDto{
    Long id;
    String text;
    String commentAuthor;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.commentAuthor = comment.getUser().getNickname();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
