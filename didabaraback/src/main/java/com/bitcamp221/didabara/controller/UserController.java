package com.bitcamp221.didabara.controller;


import com.bitcamp221.didabara.dto.DefaultRes;
import com.bitcamp221.didabara.dto.ResponseDTO;
import com.bitcamp221.didabara.dto.UserDTO;
import com.bitcamp221.didabara.model.EmailConfigEntity;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.presistence.EmailConfigRepository;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.security.TokenProvider;
import com.bitcamp221.didabara.service.UserInfoService;
import com.bitcamp221.didabara.service.UserService;
import com.bitcamp221.didabara.util.ChangeType;
import com.bitcamp221.didabara.util.ResponseMessage;
import com.bitcamp221.didabara.util.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class UserController {

    private final UserService userService;
    private final UserInfoService userInfoService;


    /**
     * 회원가입
     **/
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {

            if (userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password value");
            }

            UserEntity registeredUser = userService.creat(userDTO.toEntity());

            // 유저 테이블 생성시 유저 인포 테이블도 생성(JPA 사용 예정)
            userInfoService.create(registeredUser);

            return ResponseEntity.ok().body(registeredUser.toSingUpDTO());

        } catch (Exception e) {

            return ChangeType.toException(e);
        }

    }

    /**
     * 로그인
     */
    @PostMapping(value = "/signin")
    public ResponseEntity authenticate(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.auth(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.ok().body(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, user));
    }

    /**
     * 수정
     */
    @PutMapping("/user")
    public ResponseEntity update(@RequestBody UserDTO userDTO, @AuthenticationPrincipal String userId) {
        userService.update(userDTO.toEntity());
        return ResponseEntity.ok().body(DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_USER));
    }

    /**
     * 삭제
     */
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO, @AuthenticationPrincipal String userId) {
        userService.deleteUser(userDTO.toEntity());
        return ResponseEntity.ok().body(DefaultRes.res(StatusCode.OK,ResponseMessage.DELETE_USER));
    }


    /**
     * /* 카카오 로그인
     */
    @GetMapping("/kakao")
    public ResponseEntity<?> kakaoCallback(@Param("code") String code) {
        try {
            String[] access_Token = userService.getKaKaoAccessToken(code);

            String access_found_in_token = access_Token[0];

            // 배열로 받은 토큰들의 accsess_token만 createKaKaoUser 메서드로 전달
            return ResponseEntity.ok().body(userService.createKakaoUser(access_found_in_token));

        } catch (Exception e) {
            return ChangeType.toException(e);
        }
    }

}