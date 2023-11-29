package com.example.newsfeed.service;

import com.example.newsfeed.dto.LoginRequestDto;
import com.example.newsfeed.dto.PwdCheckRequestDto;
import com.example.newsfeed.dto.UserResponseDto;
import com.example.newsfeed.dto.UserUpdateRequestdTO;
import com.example.newsfeed.dto.SignupRequestDto;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.UserRepository;
import com.example.newsfeed.userdetails.UserDetailsImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private User user;
    private String username, email, nickname, pwd, profile, pwdcheck;
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

    public UserResponseDto viewMypage(UserDetailsImpl userDetails){
        if(userDetails!=null) {
            user = userDetails.getUser();
        } else {
            throw new NullPointerException("로그인 된 회원이 아닙니다.");
        }
        return new UserResponseDto(user);
    }
    public void checkPwd(PwdCheckRequestDto pwdCheckRequestDto, UserDetailsImpl userDetails){
        pwd = pwdCheckRequestDto.getPwd();
        pwdcheck = userDetails.getUser().getPwd();
        if(!passwordEncoder.matches(pwd, pwdcheck)){
            System.out.println(pwdcheck);
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public UserResponseDto updateUserService(UserUpdateRequestdTO userUpdateRequestdTO, UserDetailsImpl userDetails) {
        user = userDetails.getUser();
        user.updateUser(userUpdateRequestdTO);
        userRepository.save(user);
        return new UserResponseDto(user);
    }
}
