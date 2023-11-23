package com.example.newsfeed.userdetails;

import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// UserDetailsService 클래스가 이미 존재해서 클래스 객체를 사용할 때 문제가 생겨서 이름을 바꿨습니다.
@RequiredArgsConstructor
@Service
public class UserDetailsImplService {

    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다." + username));
        return new UserDetailsImpl(user);
    }
}
