package com.example.newsfeed.controller;


import com.example.newsfeed.dto.LoginRequestDto;
import com.example.newsfeed.dto.SignupRequestDto;
import com.example.newsfeed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        userService.signup((signupRequestDto));
    }

}
