package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.model.UserInfoEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserUserInfoDTO {
  private String nickname;
  private String job;
  private String password;
  private String username;
  private String realName;

  @Override
  public String toString() {
    return "nickname:" + nickname + " job:" + job + " password:" + password + " username:" + username + " realName:" + realName;
  }
}
