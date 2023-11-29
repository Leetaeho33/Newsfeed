package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Menu;
import com.example.newsfeed.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class UserResponseDto extends CommonResponseDto{

    private String email;
    private String nickname;
    private String profile;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profile = user.getProfile();
    }
}
