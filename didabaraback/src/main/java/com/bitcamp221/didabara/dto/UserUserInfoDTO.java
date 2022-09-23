package com.bitcamp221.didabara.dto;

import lombok.*;

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
  private String phoneNumber;

  public UserUserInfoDTO(){

  }
  @Override
  public String toString() {
    return "nickname:" + nickname + " job:" + job + " password:" + password + " username:" + username + " realName:" + realName + "phoneNumber: " + phoneNumber;
  }
}
