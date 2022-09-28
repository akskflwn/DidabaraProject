package com.bitcamp221.didabara.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    private Long id;
    private String username;
    private String nickname;

    private String phoneNumber;
    private String realName;


}
