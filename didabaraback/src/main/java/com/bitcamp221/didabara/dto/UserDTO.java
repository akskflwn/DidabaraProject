package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.EmailConfigRepository;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

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
  private LocalDate createdDate;
  private LocalDate modifiedDate;

  private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


  //  DB에서 꺼내온 Entity를 new UserDTO(userEntity)를 하여서
//  DTO로 변환해서 사용!
  public UserDTO(UserEntity userEntity) {
    this.id = userEntity.getId();
    this.username = userEntity.getUsername();
    this.password = userEntity.getPassword();
    this.nickname = userEntity.getNickname();
    this.createdDate = userEntity.getCreatedDate();
    this.modifiedDate = userEntity.getModifiedDate();
  }

  public UserDTO(String username, String nickname, String password) {
    this.username = username;
    this.nickname = nickname;
    this.password = password;
  }

  //  DB에 데이터를 전송하기 이전에 해당 메소드를 거쳐서 Entity타입으로 변환 후에
  //  DB에 접근!
  public static UserEntity toEntity(final UserDTO userDTO) {
    return UserEntity.builder()
            .id(userDTO.getId())
            .username(userDTO.getUsername())
            .password(passwordEncoder.encode(userDTO.getPassword()))
            .nickname(userDTO.getNickname())
            .realName(userDTO.getRealName())
            .phoneNumber(userDTO.getPhoneNumber())
            .build();
  }
}
