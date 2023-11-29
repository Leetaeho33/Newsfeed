package com.example.newsfeed.controller;

import com.example.newsfeed.dto.CommonResponseDto;
import com.example.newsfeed.dto.MenuRequestDto;
import com.example.newsfeed.dto.MenuResponseDto;
import com.example.newsfeed.dto.MenuResponseListDto;
import com.example.newsfeed.service.MenuService;
import com.example.newsfeed.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> post(@RequestBody MenuRequestDto menuRequestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){

        MenuResponseDto menuResponseDto = menuService.post(menuRequestDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(menuResponseDto);
    }

    // 메인페이지에서 보여줄 게시글 전체 조회
    // /get을 달아준 이유는 WebSecurityConfig에서 /get/**로 /get url을 갖는 모든 요청은 허가해놨기 때문
    @GetMapping("/get")
    public ResponseEntity<CommonResponseDto> getAll(){
        MenuResponseListDto menuResponseDtoListDto = new MenuResponseListDto();
        try {
            menuResponseDtoListDto.setMenuResponseDtoList(getMainPage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(menuResponseDtoListDto);
    }

    // 게시글 한개 조회
    @GetMapping("/get/menu/{menuId}")
    public ResponseEntity<CommonResponseDto> getMenuByMenuId(@PathVariable Long menuId) {
        MenuResponseDto menuResponseDto;
        try {
            menuResponseDto = menuService.getMenuByMenyId(menuId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body(menuResponseDto);
    }

    // 검색 유저의 게시글 조회
    @GetMapping("/get/user/{userId}")
    public ResponseEntity<CommonResponseDto> getMenuByUserId(@PathVariable Long userId){
        MenuResponseListDto menuResponseDtoListDto = new MenuResponseListDto();
        try {
            menuResponseDtoListDto.setMenuResponseDtoList(menuService.getMenuByUserId(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body(menuResponseDtoListDto);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<CommonResponseDto> updateMenu(@RequestBody MenuRequestDto menuRequestDto,
                                                      @PathVariable Long menuId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        MenuResponseDto menuResponseDto;
        try{
            menuResponseDto =  menuService.updateMenu(menuRequestDto, menuId, userDetails);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body(menuResponseDto);
    }

    //삭제 후 메인페이지로 보내기 위해 getMainPage 메서드 사용
    @DeleteMapping("/{menuId}")
    public ResponseEntity<CommonResponseDto> deleteMenu(@PathVariable Long menuId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        MenuResponseListDto menuResponseListDto = new MenuResponseListDto();
        try{
            menuService.deleteMenu(menuId, userDetails);
            menuResponseListDto.setMenuResponseDtoList(getMainPage());
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body(menuResponseListDto);
    }

    // 메인페이지로 보낼 dto 만드는 메서드
    private List<MenuResponseDto> getMainPage(){
        List<MenuResponseDto> menuResponseDtoList = menuService.getAll();
        return menuResponseDtoList;
    }

}
