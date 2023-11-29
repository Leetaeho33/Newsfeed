package com.example.newsfeed.controller;

import com.example.newsfeed.dto.*;
import com.example.newsfeed.dto.SignupRequestDto;
import com.example.newsfeed.jwt.JwtUtil;
import com.example.newsfeed.service.UserService;
import com.example.newsfeed.userdetails.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Validated @RequestBody SignupRequestDto signupRequestDto,
                                                    BindingResult bindingResult) {
        // 정규표현식 유효성을 판단하기 위해
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
                return ResponseEntity.badRequest().body(
                        new CommonResponseDto(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST.value()));
            }
        }try {
            userService.signup(signupRequestDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(
                    new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body((
                new CommonResponseDto("성공적으로 회원가입에 성공했습니다.", HttpStatus.OK.value())));
    }
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                                   HttpServletResponse httpResponse){
        try {
            userService.login(loginRequestDto);
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        httpResponse.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername()));

        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok().body(new CommonResponseDto("로그아웃 성공", HttpStatus.OK.value()));
    }

    // url이랑(GetMapping으로 수정), 메서드명 수정(RESTfUL하게)
    @GetMapping ("/mypage")
    public ResponseEntity<CommonResponseDto> mypage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        UserResponseDto userResponseDto;
        try {
            userResponseDto = userService.viewMypage(userDetails);
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(userResponseDto);
    }

    // url이랑, 메서드명 수정(RESTfUL하게)
    @PostMapping("/checkpwd")
    public ResponseEntity<CommonResponseDto> checkPwd(@RequestBody PwdCheckRequestDto pwdCheckRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            userService.checkPwd(pwdCheckRequestDto,userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("회원정보 수정페이지로 가기", HttpStatus.OK.value()));
    }

    @PutMapping("/update")
    public ResponseEntity<CommonResponseDto> update(@RequestBody UserUpdateRequestdTO userRequestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        UserResponseDto userResponseDto;
        try {
            userResponseDto = userService.updateUserService(userRequestDto, userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body(userResponseDto);
    }
}
