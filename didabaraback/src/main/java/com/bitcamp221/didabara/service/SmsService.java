package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.mapper.UserMapper;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SmsService {

  final String api_key = "";
  final String api_secret = "";
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public String[] certifiedPhoneNumber(String phoneNum) {
    Message coolsms = new Message(api_key, api_secret);
    // 인증코드 6자리 생성
    String code = UUID.randomUUID().toString().substring(0, 6);

    String username = userMapper.findUserPhoneNumber(phoneNum);
    System.out.println("username = " + username);

//        List<MultipartFile>

    // 4 params(to, from, type, text) are mandatory. must be filled
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("to", phoneNum);    // 수신전화번호
    params.put("from", "핸드폰 번호");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
    params.put("type", "SMS");
    params.put("text", "didabara 휴대폰인증 테스트 메시지 : 인증번호는" + "[" + code + "]" + "입니다.");
    params.put("app_version", "test app 1.2"); // application name and version

    try {
      JSONObject send = coolsms.send(params);
      String[] usernameAndcode = new String[2];
      usernameAndcode[0] = code;
      usernameAndcode[1] = username;

      System.out.println("send.toString() = " + send.toString());
      return usernameAndcode;
    } catch (CoolsmsException e) {
      throw new RuntimeException(e);
    }
  }

  public String[] certifiedPhoneNumberAndTempCode(String phoneNum) {
    Message coolsms = new Message(api_key, api_secret);
    // 인증코드 6자리 생성
    String code = UUID.randomUUID().toString().substring(0, 6);

    String username = userMapper.findUserPhoneNumber(phoneNum);
    System.out.println("username = " + username);

//        List<MultipartFile>

    // 4 params(to, from, type, text) are mandatory. must be filled
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("to", phoneNum);    // 수신전화번호
    params.put("from", "01055821376");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
    params.put("type", "SMS");
    params.put("text", "didabara 휴대폰인증 테스트 메시지 : 인증번호는" + "[" + code + "]" + "입니다.");
    params.put("app_version", "test app 1.2"); // application name and version

    try {
      JSONObject send = coolsms.send(params);
      String tempPassword = UUID.randomUUID().toString().substring(0, 8);
      String[] usernameAndcode = new String[3];
      usernameAndcode[0] = code;
      usernameAndcode[1] = username;
      usernameAndcode[2] = tempPassword;

      UserEntity byPhoneNumber = userRepository.findByPhoneNumber(phoneNum);
      String encodePwd = passwordEncoder.encode(tempPassword);
      byPhoneNumber.setPassword(encodePwd);

      UserEntity save = userRepository.save(byPhoneNumber);

      return usernameAndcode;
    } catch (CoolsmsException e) {
      throw new RuntimeException(e);
    }
  }
}


