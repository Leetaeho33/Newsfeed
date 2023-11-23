package com.example.newsfeed.service;


import com.example.newsfeed.dto.LoginRequestDto;
import com.example.newsfeed.dto.SignupRequestDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.UserRepository;
import com.fasterxml.jackson.core.SerializableString;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private User user;
    private String username, email, nickname, pwd, profile;
    private final PasswordEncoder passwordEncoder;
    Optional<User> userOptional;
    public void signup(SignupRequestDto signupRequestDto) {
        username = signupRequestDto.getUsername();
        pwd = passwordEncoder.encode(signupRequestDto.getPwd());
        nickname = signupRequestDto.getNickname();
        email = signupRequestDto.getEmail();
        profile = signupRequestDto.getProfile();
        userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            throw new IllegalArgumentException("중복된 회원 아이디가 있습니다.");
        }
        userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw new IllegalArgumentException("중복된 email이 있습니다.");
        }
        userOptional = userRepository.findByNickname(nickname);
        if(userOptional.isPresent()){
            throw new IllegalArgumentException("중복된 닉네임이 있습니다.");
        }
        user = new User(username, pwd, nickname, email, profile);
        userRepository.save(user);
    }

    public void login(LoginRequestDto loginRequestDto) {
        username = loginRequestDto.getUsername();
        pwd = loginRequestDto.getPwd();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));
        if(!passwordEncoder.matches(pwd, user.getPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

}
