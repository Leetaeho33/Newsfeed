package com.example.newsfeed.repository;

import com.example.newsfeed.dto.MenuRequestDto;
import com.example.newsfeed.entity.Menu;
import com.example.newsfeed.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MenuRepository Test")
@DataJpaTest
@ActiveProfiles("test")
class MenuRepositoryTest {

    Menu menu, result;
    MenuRequestDto menuRequestDto = new MenuRequestDto();
    User user= new User();
    @Autowired
    MenuRepository menuRepository;

    @DisplayName("given")
    @BeforeEach
    void setup(){
        //given
        user.setId(1L);
        user.setUsername("xogh");
        user.setPwd("12345678");
        user.setNickname("태호임");
        user.setProfile("태호의 한줄소개");
        user.setEmail("xogh@email.com");
    }

    @Test
    @DisplayName("Menu Save Test")
    void save() {
            //given
            menuRequestDto.setTitle("테스트용 제목");
            menuRequestDto.setContent("테스트용 내용");

            menu = new Menu(menuRequestDto);
            menu.setUser(user);
            //when
            result = menuRepository.save(menu);

            //then
            assertEquals(result.getId(), menu.getId());
            assertEquals(result.getTitle(), menu.getTitle());
            assertEquals(result.getContent(), menu.getContent());
    }

    @Test
    @DisplayName("Find by MenuId Test")
    void findByMenuId(){
        //given
        //메뉴 저장
        menuRequestDto.setTitle("테스트용 제목");
        menuRequestDto.setContent("테스트용 내용");

        menu = new Menu(menuRequestDto);
        menu.setUser(user);
        menuRepository.save(menu);

        //when
        result = menuRepository.findById(menu.getId()).get();

        //then
        assertEquals(result, menu);
    }

    @DisplayName("findMenuByUserId Method Test")
    @Test
    void findMenuByUserId() {
        //given
        menuRequestDto.setTitle("테스트용 제목");
        menuRequestDto.setContent("테스트용 내용");

        //첫번째 메뉴 저장
        menu = new Menu(menuRequestDto);
        menu.setUser(user);
        menuRepository.save(menu);

        //두번째 메뉴 저장
        menuRequestDto.setTitle("테스트용 제목2");
        menuRequestDto.setContent("테스트용 내용2");

        Menu menu2 = new Menu(menuRequestDto);
        menu2.setUser(user);
        menuRepository.save(menu2);

        List<Menu> resultList;
        //when
        resultList = menuRepository.findMenuByUserId(menu.getUser().getId());

        //then
        assertEquals(resultList.get(0).getId(), menu.getId());
        assertEquals(resultList.get(1).getId(), menu2.getId());
    }

    @DisplayName("findMenuByUserId isEmpty() Test")
    @Test
    void findMenuByUserIdFailTest() {
        //given
        menuRequestDto.setTitle("테스트용 제목");
        menuRequestDto.setContent("테스트용 내용");

        //첫번째 메뉴 저장
        menu = new Menu(menuRequestDto);
        menu.setUser(user);
        menuRepository.save(menu);

        //두번째 메뉴 저장
        menuRequestDto.setTitle("테스트용 제목2");
        menuRequestDto.setContent("테스트용 내용2");

        Menu menu2 = new Menu(menuRequestDto);
        menu2.setUser(user);
        menuRepository.save(menu2);

        List<Menu> resultList;
        //when
        resultList = menuRepository.findMenuByUserId(3L);

        //then
        assertTrue(resultList.isEmpty());
    }

    @DisplayName("findAll(Sort.by(Sort.Direction.DESC, \"createdAt\")")
    @Test
    void findAllTest(){
        //given
        menuRequestDto.setTitle("테스트용 제목");
        menuRequestDto.setContent("테스트용 내용");

        //첫번째 메뉴 저장
        menu = new Menu(menuRequestDto);
        menu.setUser(user);
        menuRepository.save(menu);

        //두번째 메뉴 저장
        menuRequestDto.setTitle("테스트용 제목2");
        menuRequestDto.setContent("테스트용 내용2");

        Menu menu2 = new Menu(menuRequestDto);
        menu2.setUser(user);
        menuRepository.save(menu2);

        List<Menu> resultList;

        //when
        resultList = menuRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        //then
        assertEquals(resultList.get(0), menu2);
        assertEquals(resultList.get(1), menu);
    }

    @Test
    @DisplayName("Delete Method Test")
    void deleteTest(){
        //given
        //메뉴 저장
        menuRequestDto.setTitle("테스트용 제목");
        menuRequestDto.setContent("테스트용 내용");

        menu = new Menu(menuRequestDto);
        menu.setUser(user);
        menuRepository.save(menu);
        result = menuRepository.findById(menu.getId()).get();

        //when
        menuRepository.delete(menu);

        //then
        Optional<Menu> findMenu = menuRepository.findById(menu.getId());
        assertFalse(findMenu.isPresent());
    }
}