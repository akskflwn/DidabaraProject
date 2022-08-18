package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity creat(final UserEntity userEntity) {
//    1. userEntity 유효성 검사.
        if (userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("invalid arguments");
        }

        final String username = userEntity.getUsername();
        final String nickname = userEntity.getNickname();

        //  2. 중복 검사
        if (userRepository.existsByUsername(username)) {
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        // 3. 닉네임 중복검사
        if (userRepository.existsByNickname(nickname)) {
            log.warn("Nickname already exists {}", nickname);
            throw new RuntimeException("Nickname already exists");
        }
        return userRepository.save(userEntity);
    }

    //  아이디 & 비밀번호 일치 확인
    public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder passwordEncoder) {
        final UserEntity originalUser = userRepository.findByUsername(username);

//    matches
        if (originalUser != null && passwordEncoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }

        return null;
    }
}