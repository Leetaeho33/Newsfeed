package com.example.newsfeed.entity;

import com.example.newsfeed.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user = User.builder().username("user").
                                pwd("12345678").
                                email("user@email.com").
                                profile("유저에요.").
                                nickname("유저").build();

    @DisplayName("기본 유저 셋업입니다.")
    @BeforeAll
    public static void setUp(){
        System.out.println("유저 Entity 테스트 시작");
    }
    @DisplayName("유저 Builder 테스트 입니다.")
    @Test
    public void buildUserTest(){
        // given & when
        User xogh = User.builder().
                username("xogh").
                pwd("12345678").
                email("xogh@email.com").
                nickname("태호야").
                profile("태호입니다.").build();

        User thdns = User.builder().
                username("thdns").
                pwd("12345678").
                email("thdns@email.com").
                nickname("소운이").
                profile("소운이에요.").build();

        // then
        assertEquals(thdns.getUsername(), "thdns");
        assertEquals(thdns.getPwd(), "12345678");
        assertEquals(thdns.getEmail(), "thdns@email.com");
        assertEquals(thdns.getNickname(), "소운이");
        assertEquals(thdns.getProfile(), "소운이에요.");

        assertEquals(xogh.getUsername(), "xogh");
        assertEquals(xogh.getPwd(), "12345678");
        assertEquals(xogh.getEmail(), "xogh@email.com");
        assertEquals(xogh.getNickname(), "태호야");
        assertEquals(xogh.getProfile(), "태호입니다.");
    }

    @DisplayName("유저 update 테스트 입니다.")
    @Test
    void updateUser() {
    }

}