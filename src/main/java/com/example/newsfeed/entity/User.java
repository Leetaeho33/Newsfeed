package com.example.newsfeed.entity;

import com.example.newsfeed.dto.SignupRequestDto;
import com.example.newsfeed.dto.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String pwd;
    @Column
    private String nickname;
    @Column
    private String email;
    @Column
    private String profile;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Menu> menu;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList;

    @Builder
    public User(String username, String pwd, String nickname, String email, String profile) {
        this.username = username;
        this.pwd = pwd;
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
    }


    public User(SignupRequestDto signupRequestDto){
        this.username = signupRequestDto.getUsername();
        this.pwd = signupRequestDto.getPwd();
        this.nickname = signupRequestDto.getNickname();
        this.email = signupRequestDto.getEmail();
        this.profile = signupRequestDto.getProfile();
    }

        public void updateUser(String email, String nickname, String profile){
            this.email = email;
            this.nickname = nickname;
            this.profile = profile;
    }
}
