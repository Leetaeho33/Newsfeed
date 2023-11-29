package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Menu;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Getter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuResponseDto extends CommonResponseDto{
    private String title;
    private String content;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public MenuResponseDto(Menu menu) {
        this.title = menu.getTitle();
        this.content = menu.getContent();
        this.authorNickname = menu.getUser().getNickname();
        this.createdAt = menu.getCreatedAt();
        this.modifiedAt = menu.getModifiedAt();
//        this.commentList = menu.getCommentList().stream()
//                .map(CommentResponseDto::new)
//                .toList();

        // 여기서 NPE 떴는데 이유는 20번째 줄 new ArrayList<>()로 초기화를 안시켜줌.
        // 그렇게 되면 초기값이 NULL이라서 NPE가 떠버림!
        // 저기 위에 주석 처리된건 진짜 고수들의 문법, stream을 사용해서 Comment Entity -> CommentResponseDto로 변환시켜줌
        for (Comment comment : menu.getCommentList()) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            this.commentList.add(commentResponseDto);
        }
    }


}
