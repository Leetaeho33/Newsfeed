package com.example.newsfeed.entity;

import com.example.newsfeed.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("User Entity Test.")
class UserTest {
    @Mock
    UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
    @Mock
    User user = User.builder().username("user").
                                pwd("12345678").
                                email("user@email.com").
                                profile("유저에요.").
                                nickname("유저").build();

    @BeforeAll
    public static void setUp(){
        System.out.println("유저 Entity 테스트 시작");
    }
    @DisplayName("유저 Builder 테스트 입니다.")
    @Test
    public void buildUserTest(){
        // given & when
        User xogh = User.builder().
                username("xogh").
                pwd("12345678").
                email("xogh@email.com").
                nickname("태호야").
                profile("태호입니다.").build();

        User thdns = User.builder().
                username("thdns").
                pwd("12345678").
                email("thdns@email.com").
                nickname("소운이").
                profile("소운이에요.").build();

        // then
        assertEquals(thdns.getUsername(), "thdns");
        assertEquals(thdns.getPwd(), "12345678");
        assertEquals(thdns.getEmail(), "thdns@email.com");
        assertEquals(thdns.getNickname(), "소운이");
        assertEquals(thdns.getProfile(), "소운이에요.");

        assertEquals(xogh.getUsername(), "xogh");
        assertEquals(xogh.getPwd(), "12345678");
        assertEquals(xogh.getEmail(), "xogh@email.com");
        assertEquals(xogh.getNickname(), "태호야");
        assertEquals(xogh.getProfile(), "태호입니다.");
    }

    @Nested
    @DisplayName("유저 update 테스트 묶음입니다..")
    public class UserUpdate {
        @DisplayName("유저 update 테스트(UserRequestDto가 NULL일 때")
        @Test
        void updateUserRequestDtoNull() {
            // given
            User xogh = User.builder().
                    username("xogh").
                    pwd("12345678").
                    email("xogh@email.com").
                    nickname("태호야").
                    profile("태호입니다.").build();

            User thdns = User.builder().
                    username("thdns").
                    pwd("12345678").
                    email("thdns@email.com").
                    nickname("소운이").
                    profile("소운이에요.").build();

            // when
            xogh.updateUser(null, null ,null);
            thdns.updateUser(null, null ,null);
            user.updateUser(null, null ,null);

            // then
            assertNull(xogh.getEmail());
            assertNull(xogh.getNickname());
            assertNull(xogh.getProfile());

            assertNull(thdns.getEmail());
            assertNull(thdns.getNickname());
            assertNull(thdns.getProfile());

            assertNull(user.getEmail());
            assertNull(user.getProfile());
            assertNull(user.getNickname());
        }
        @DisplayName("유저 update")
        @Test
        void updateUser() {
            // given
            User xogh = User.builder().
                    username("xogh").
                    pwd("12345678").
                    email("xogh@email.com").
                    nickname("태호야").
                    profile("태호입니다.").build();


            User thdns = User.builder().
                    username("thdns").
                    pwd("12345678").
                    email("thdns@email.com").
                    nickname("소운이").
                    profile("소운이에요.").build();


            // when
            xogh.updateUser("changedxogh@email.com", "수정된 태호", "수정된 태호 프로필");
            thdns.updateUser("changedthdns@email.com", "수정된 소운", "수정된 소운 프로필");
            user.updateUser("changedxogh@email.com","수정된 태호", "수정된 태호 프로필");

            // then
            assertEquals(xogh.getEmail(),"changedxogh@email.com" );
            assertEquals(xogh.getNickname(), "수정된 태호");
            assertEquals(xogh.getProfile(), "수정된 태호 프로필");

            assertEquals(thdns.getEmail(),"changedthdns@email.com" );
            assertEquals(thdns.getNickname(), "수정된 소운");
            assertEquals(thdns.getProfile(), "수정된 소운 프로필");

            assertEquals(user.getEmail(), "changedxogh@email.com");
            assertEquals(user.getNickname(), "수정된 태호");
            assertEquals(user.getProfile(), "수정된 태호 프로필");
        }
    }
}

