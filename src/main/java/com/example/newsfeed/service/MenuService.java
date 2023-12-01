package com.example.newsfeed.service;

import com.example.newsfeed.dto.MenuRequestDto;
import com.example.newsfeed.dto.MenuResponseDto;
import com.example.newsfeed.entity.Menu;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.MenuRepository;
import com.example.newsfeed.userdetails.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    MenuResponseDto menuResponseDto;
    private Menu menu;
    private User user;
    public MenuResponseDto post(MenuRequestDto menuRequestDto, UserDetailsImpl userDetails) {
        user = userDetails.getUser();
        menu = new Menu(menuRequestDto);
        menu.setUser(user);
        // 회원 인증 기능 추가
        menu = menuRepository.save(menu);
        return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> getAll() {
        List<Menu> menuList = menuRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();
        for (Menu m : menuList) {
            menuResponseDto = new MenuResponseDto(m);
            menuResponseDtoList.add(menuResponseDto);
        }
            return menuResponseDtoList;
    }

    public MenuResponseDto getMenuByMenuId(Long menuId){
        menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 ID 입니다."));
        return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> getMenuByUserId(Long userId) {
        List<Menu> menuList = menuRepository.findMenuByUserId(userId);
        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();
        for (Menu m : menuList) {
            menuResponseDto = new MenuResponseDto(m);
            menuResponseDtoList.add(menuResponseDto);
        }
        if(menuResponseDtoList.isEmpty()){
            throw  new IllegalArgumentException("해당 유저는 글을 작성하지 않았습니다.");
        }
        return menuResponseDtoList;
    }

    @Transactional
    public MenuResponseDto updateMenu(MenuRequestDto menuRequestDto, Long menuId, UserDetailsImpl userDetails) {
        user = userDetails.getUser();
        menu = getMenu(menuId);
        if(user.getUsername().equals(menu.getUser().getUsername())) {
            menu.updateMenu(menuRequestDto);
        }else{
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }

        return new MenuResponseDto(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId, UserDetailsImpl userDetails) {
        user = userDetails.getUser();
        menu = getMenu(menuId);
        if(user.getUsername().equals(menu.getUser().getUsername())){
            menuRepository.delete(menu);
        }else{
            throw new RejectedExecutionException("작성자만 삭제할 수 있습니다.");
        }
    }

    public Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 ID 입니다."));
    }

}

