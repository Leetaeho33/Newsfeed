package com.example.newsfeed.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    Long menuId;
    @Size(min = 1, max = 20,message = "20자 이상 댓글을 달 수 없습니다.")
    String text;
}
