package com.example.newsfeed.service;

import com.example.newsfeed.dto.MenuRequestDto;
import com.example.newsfeed.dto.MenuResponseDto;
import com.example.newsfeed.entity.Menu;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.MenuRepository;
import com.example.newsfeed.userdetails.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DisplayName("MenuService Test")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MenuServiceTest {

    @InjectMocks
    MenuService menuService;
    @Mock
    MenuRepository menuRepository;
    Menu menu;
    User user;
    UserDetailsImpl userDetails;
    Long fakeId = 1L;
    MenuRequestDto menuRequestDto = new MenuRequestDto();
    MenuResponseDto menuResponseDto, result;

    @BeforeEach
    @DisplayName("User & UserDetails Setup")
    void setup(){
        user = User.builder().
                username("xogh").
                pwd("12345678").
                email("xoth@email.com").nickname("태호야").profile("한줄소개").
                build();
        user.setId(1L);
        userDetails = new UserDetailsImpl(user);
    }

    @Test
    @DisplayName("Post Test")
    void postTest() {
        // given
        menuRequestDto.setTitle("게시글 제목");
        menuRequestDto.setContent("게시글 내용");
        menu = new Menu(menuRequestDto);
        menu.setId(fakeId);
        menu.setUser(user);

        Menu resultMenu = new Menu(menuRequestDto);
        resultMenu.setUser(user);
        // 객체를 테스트할 때 넘겨주면 너무 어려워짐 왜냐하면 post 메서드를 들어가보면
        // MenuService의 30번째 줄을 보면 Menu menu = new Menu(MenuRequeestDto requestDto)를 해서 새로 생성을 해줌.
        // 근데 Test에서는 저걸 새로 생성을 할 때 인자인 requestDto를 줄 수가 없음. -> menu가 null이 됨.
        // 그래서 save()와 같이 인자에 객체를 넣을 때는 그냥 any()를 씀. 어짜피 짜고치는 test니까 결과값만 나오면 됨.
        // 하지만 findById와같이 Long같은 데이터를 넘길 때는 괜찮음. 왜냐하면 새로 생성을 해주지 않기 때문에
        // 객체가 불일치 하는 경우가 나지 않기 때문
        // mock객체인 menuRepository는 save 메서드를 실제로 사용못함. 따라서 given()과 willReturn()을 이용해줌
        // 위의 것이 가능한 것은 Repository Test를 통해 Save 메서드가 잘 작동하는지 테스트를 했기 때문
        given(menuRepository.save(any())).willReturn(resultMenu);

        // when
        menuResponseDto = menuService.post(menuRequestDto, userDetails);
        result = new MenuResponseDto(resultMenu);

        // then
        assertEquals(result.getAuthorNickname(), menuResponseDto.getAuthorNickname());
        assertEquals(result.getTitle(), menuResponseDto.getTitle());
        assertEquals(result.getContent(), menuResponseDto.getContent());
    }


    @Test
    void getAll() {
        //given
        menuRequestDto.setTitle("게시글 제목");
        menuRequestDto.setContent("게시글 내용");
        menu = new Menu(menuRequestDto);
        menu.setId(fakeId);
        menu.setUser(user);

        menuRequestDto.setTitle("게시글 제목2");
        menuRequestDto.setContent("게시글 내용2");
        Menu menu2 = new Menu(menuRequestDto);
        menu2.setId(2L);
        menu2.setUser(user);

        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        menuList.add(menu2);

        given(menuRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))).willReturn(menuList);

        //when
        List<MenuResponseDto> resultList;
        resultList = menuService.getAll();

        //then
        assertEquals(resultList.get(0).getId(), menu.getId());
        assertEquals(resultList.get(1).getId(), menu2.getId());
    }

    @Test
    void getMenuByMenyId() {
    }

    @Test
    void getMenuByUserId() {
    }

    @Test
    void updateMenu() {
        //givne
        menuRequestDto.setTitle("수정된 글 제목");
        menuRequestDto.setContent("수정된 글 내용");
        //menuId로 찾은 menu를 given
        given(menuRepository.findById(menu.getId())).willReturn(Optional.of(menu));
        menu.updateMenu(menuRequestDto);
        //when

        //then
    }

    @Test
    void deleteMenu() {
    }

    @Test
    void getMenu() {
    }
}
