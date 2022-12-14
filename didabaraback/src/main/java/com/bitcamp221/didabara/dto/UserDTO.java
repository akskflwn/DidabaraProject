package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.UserEntity;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String nickname;

    private String token;
    private String phoneNumber;
    private String realName;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDTO(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .realName(realName)
                .phoneNumber(phoneNumber)
                .build();
    }
}
