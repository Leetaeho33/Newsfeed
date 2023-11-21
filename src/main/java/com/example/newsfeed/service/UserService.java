package com.example.newsfeed.service;

import com.example.newsfeed.dto.SignupRequestDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.UserRepository;
import com.fasterxml.jackson.core.SerializableString;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private User user;
    private String username, email, nickname;
    Optional<User> userOptional;
    public void signup(SignupRequestDto signupRequestDto) {
        username = signupRequestDto.getUsername();
        email = signupRequestDto.getEmail();
        nickname = signupRequestDto.getNickname();
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
        user = new User(signupRequestDto);
        userRepository.save(user);
    }

}
