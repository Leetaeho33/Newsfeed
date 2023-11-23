package com.example.newsfeed.controller;

import com.example.newsfeed.dto.CommonResponseDto;
import com.example.newsfeed.dto.MenuRequestDto;
import com.example.newsfeed.dto.MenuResponseDto;
import com.example.newsfeed.entity.Menu;
import com.example.newsfeed.repository.MenuRepository;
import com.example.newsfeed.service.MenuService;
import com.example.newsfeed.userdetails.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuResponseDto> post(@RequestBody MenuRequestDto menuRequestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(menuService.post(menuRequestDto, userDetails));
    }

    // 메인페이지에서 보여줄 게시글 전체 조회
    @GetMapping("/get")
    public ResponseEntity<List<MenuResponseDto>> getAll(){
        List<MenuResponseDto> menuResponseDtoList = getMainPage();
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(menuResponseDtoList);
    }

    // 게시글 한개 조회
    @GetMapping("/get/menus/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenuByMenuId(@PathVariable Long menuId){
        return ResponseEntity.status(HttpStatus.OK.value()).body(menuService.getMenuByMenyId(menuId));
    }

    // 검색 유저의 게시글 조회
    @GetMapping("/get/users/{userId}")
    public ResponseEntity<List<MenuResponseDto>> getMenuByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK.value()).body(menuService.getMenuByUserId(userId));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(@RequestBody MenuRequestDto menuRequestDto,
                                                      @PathVariable Long menuId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        MenuResponseDto menuResponseDto =  menuService.updateMenu(menuRequestDto, menuId, userDetails);
        return ResponseEntity.status(HttpStatus.OK.value()).body(menuResponseDto);
    }

    //삭제 후 메인페이지로 보내기 위해 getMainPage 메서드 사용
    @DeleteMapping("/{menuId}")
    public ResponseEntity<List<MenuResponseDto>> deleteMenu(@PathVariable Long menuId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        menuService.deleteMenu(menuId, userDetails);
        List<MenuResponseDto> menuResponseDtoList = getMainPage();
        return ResponseEntity.status(HttpStatus.OK.value()).body(menuResponseDtoList);
    }

    // 메인페이지로 보낼 dto 만드는 메서드
    private List<MenuResponseDto> getMainPage(){
        List<MenuResponseDto> menuResponseDtoList = menuService.getAll();
        return menuResponseDtoList;
    }

}
