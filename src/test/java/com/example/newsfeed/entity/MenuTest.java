package com.example.newsfeed.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Menu 테스트입니다.")
class MenuTest {

    private List<Comment> commentList = new ArrayList<>();
    private Comment comment = new Comment();
    @Test
    @BeforeEach
    @DisplayName("메뉴테스트 셋업입니다.")
    public void setup(){
        System.out.println("Menu 테스트 시작");

    }
    @Test
    void addComment() {
    }
}