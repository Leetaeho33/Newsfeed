package com.example.newsfeed.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.websocket.server.ServerEndpoint;
import lombok.*;


@Setter
@Getter
public class UserUpdateRequestDto {
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(min= 2, max= 5, message = "닉네임의 길이는 2~5글자 사이입니다.")
    String nickname;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "email 형식과 맞지 않습니다.")
    String email;

    @Size(min= 1, max= 15, message = "한줄 소개의 길이는 1~15글자 사이입니다.")
    String profile;
}
