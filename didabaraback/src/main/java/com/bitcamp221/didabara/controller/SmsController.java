package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
@Slf4j
public class SmsController {

  private final SmsService smsService;

  private final UserRepository userRepository;

  @PostMapping("/findusername")
  public ResponseEntity<?> checkUserSms(@RequestParam("userPhoneNum") String userPhoneNum, @RequestParam("realName") String realName) {
    if (userPhoneNum == null || realName == null) {
      return ResponseEntity.badRequest().body("입력해주세요");
    }

    String decodeName = null;
    decodeName = URLDecoder.decode(realName, StandardCharsets.UTF_8);
    System.out.println("decodeName = " + decodeName);

    UserEntity byPhoneNumber = userRepository.findByPhoneNumberAndRealName(userPhoneNum, realName);

    if (byPhoneNumber.getPhoneNumber().equals(userPhoneNum) && byPhoneNumber.getRealName().equals(realName)) {
      System.out.println("userPhoneNum = " + userPhoneNum);

      String[] strings = smsService.certifiedPhoneNumber(userPhoneNum);

      Map<String, String> usernameAndCode = new HashMap<>();
      usernameAndCode.put("code", strings[0]);
      usernameAndCode.put("username", strings[1]);

      return ResponseEntity.ok().body(usernameAndCode);
    } else {
      return ResponseEntity.badRequest().body("찾는 사용자가 없습니다.");
    }


  }

  @PostMapping("/findpassword")
  public ResponseEntity<?> checkUserSms(@RequestParam(value = "userPhoneNum", required = true) String userPhoneNum,
                                        @RequestParam(value = "realName", required = true) String realName,
                                        @RequestParam(value = "username", required = true) String username) {

    String decodeRealName = URLDecoder.decode(realName, StandardCharsets.UTF_8);
    String decodeUsername = URLDecoder.decode(username, StandardCharsets.UTF_8);
    System.out.println("decodeRealName = " + decodeRealName);
    System.out.println("decodeUsername = " + decodeUsername);

    UserEntity byPhoneNumber = userRepository.findByPhoneNumberAndRealNameAndUsername(userPhoneNum, realName, username);
    System.out.println("byPhoneNumber = " + byPhoneNumber.getPhoneNumber());

    if (byPhoneNumber.getPhoneNumber().equals(userPhoneNum) && byPhoneNumber.getRealName().equals(realName) && byPhoneNumber.getUsername().equals(username)) {
      System.out.println("userPhoneNum = " + userPhoneNum);

      String[] strings = smsService.certifiedPhoneNumberAndTempCode(userPhoneNum);

      Map<String, String> usernameAndCode = new HashMap<>();
      usernameAndCode.put("code", strings[0]);
      usernameAndCode.put("username", strings[1]);
      usernameAndCode.put("tempPassword", strings[2]);

      return ResponseEntity.ok().body(usernameAndCode);
    } else {
      return ResponseEntity.badRequest().body("찾는 사용자가 없습니다.");
    }


  }

}
