package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Menu;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Getter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MenuResponseDto {
    private String title;
    private String content;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<Comment> commentList;

    public MenuResponseDto(Menu menu) {
        this.title = menu.getTitle();
        this.content = menu.getContent();
        this.authorNickname = menu.getUser().getNickname();
        this.createdAt = menu.getCreatedAt();
        this.modifiedAt = menu.getModifiedAt();
        this.commentList = menu.getCommentList();
    }

}
