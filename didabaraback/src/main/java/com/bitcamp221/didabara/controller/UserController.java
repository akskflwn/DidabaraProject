package com.bitcamp221.didabara.controller;


import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.service.EmailConfigService;
import com.bitcamp221.didabara.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.bitcamp221.didabara.dto.ResponseDTO;
import com.bitcamp221.didabara.dto.UserDTO;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.security.TokenProvider;
import com.bitcamp221.didabara.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/auth") // 매핑 주소 추가 : 김남주
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private EmailConfigService emailConfigService;

    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private TokenProvider tokenProvider;

    /** 회원 수정 (수정 해야됌)
     * 작성자 : 김남주
     *
     * @param
     * @return int | 1 이면 성공 0 이면 실패
     */
    /*@PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity) {
        // 입력 받은 userEntity 객체가 DB에 있는 값이랑 같은지 확인

        if (userService.checkUserInDB(userEntity.getUsername())){

            userEntity.setRegistDate(LocalDate.now());
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

            int userRow = userMapper.updateUser(userEntity);


            return ResponseEntity.ok().body(userRow);
        }
        //
        return ResponseEntity.badRequest().body("일치하지 않는 유저");
    }*/



    // https://kauth.kakao.com/oauth/authorize?client_id=4af7c95054f7e1d31cff647965678936&redirect_uri=http://localhost:8080/auth/kakao&response_type=code

    /* 카카오 로그인 */
    @GetMapping("/kakao")
    public ResponseEntity<?> kakaoCallback(String code) {
        log.info("code={}",code);

        String access_Token = userService.getKaKaoAccessToken(code);
        userService.createKakaoUser(access_Token);

        // 나중에 수정(리다이렉트 해야함)
        /*HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "localhost:3000/MainPage");

        String redirect_uri="localhost:3000/";
        response.sendRedirect(redirect_uri);*/

        return ResponseEntity.ok().body(access_Token);
    }

    //  회원가입
//  http://localhost:8080/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
//      받은 데이터 유효성 검사
            if (userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password value");
            }


//      요청을 이용해 저장할 유저 객체 생성
            UserEntity userEntity = UserEntity.builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .nickname(userDTO.getNickname())
//              .password(userDTO.getPassword())
                    .build();

//      서비스를 이용해 리포지터리에 유저 저장
            UserEntity registeredUser = userService.creat(userEntity);

//      응답객체 만들기(패스워드 제외)
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .nickname(registeredUser.getNickname())
                    .build();

//      유저 정보는 현재 하나이므로 리스트로 만들 필요 없음
//      ResponseDTO를 사용하지 않고 UserDTO 타입으로 반환

            return ResponseEntity.ok().body(responseUserDTO);

        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    //  로그인
//  http://localhost:8080/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.getByCredentials(
                userDTO.getUsername(),
                userDTO.getPassword(),
                passwordEncoder
        );

        if (user != null) {
//    토큰 생성.
            final String token = tokenProvider.create(user);

            final UserDTO responsUserDTO = UserDTO.builder()
                    .username(user.getUsername())
                    .id(user.getId())
//                    .token(token)
                    .build();

            return ResponseEntity.ok().body(responsUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login Failed")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }

    }



}

