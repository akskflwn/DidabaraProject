package com.bitcamp221.didabara.controller;


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
            //응답객체 만들기(패스워드,토큰 제외)
            return ResponseEntity.ok().body(registeredUser.toDTO(null));

        } catch (Exception e) {

            return ChangeType.toException(e);
        }

    }

    /**
     * 로그인
     */
    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok().body(userService.auth(userDTO.getUsername(), userDTO.getPassword()));
        } catch (Exception e) {
            return ChangeType.toException(e);
        }
    }

    /**
     * 수정
     */
    @PutMapping("/user")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO, @AuthenticationPrincipal String userId) {
        try {
            userService.update(userDTO.toEntity());
            return ResponseEntity.ok().body("업데이트 성공.");
        } catch (Exception e) {
            return ChangeType.toException(e);
        }

    }

    //삭제
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO, @AuthenticationPrincipal String userId) {

        boolean checkPwd = userService.checkPwd(userDTO, userId);

        if (checkPwd == false) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다");
        } else {
            userService.deleteUser(Long.valueOf(userId));
            return ResponseEntity.ok().body("삭제 되었습니다.");
        }

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