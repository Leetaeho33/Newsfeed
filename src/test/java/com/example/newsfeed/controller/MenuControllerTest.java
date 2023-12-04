package com.example.newsfeed.controller;

import com.example.newsfeed.config.WebSecurityConfig;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.userdetails.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(
        value = MenuController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)


class MenuControllerTest {

    @BeforeEach
    @DisplayName("User & UserDetails Setup")
    void setup(){
        // 인증 토큰이 있는 유저
        User user = User.builder().
                username("xogh").
                pwd("12345678").
                email("xoth@email.com").nickname("태호야").profile("한줄소개").
                build();
        user.setId(1L);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        // token의 claims에서 뽑아왔다고 치고
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 인증 토큰이 없는 유저
        User userNoAuth = User.builder().
                username("noAuth").
                pwd("12345678").
                email("noAuth@email.com").nickname("스파이").profile("나는 스파이다").
                build();
        user.setId(2L);
        UserDetailsImpl userDetailsNoAuth = new UserDetailsImpl(userNoAuth);
    }
    @DisplayName("post Test")
    @Test
    void post() {

    }

    @Test
    void getAll() {
    }

    @Test
    void getMenuByMenuId() {
    }

    @Test
    void getMenuByUserId() {
    }

    @Test
    void updateMenu() {
    }

    @Test
    void deleteMenu() {
    }
}