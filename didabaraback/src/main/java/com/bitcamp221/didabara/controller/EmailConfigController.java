package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.model.EmailConfigEntity;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.EmailConfigRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.service.EmailConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/emailconfig")
public class EmailConfigController {
  @Autowired
  private EmailConfigService emailConfigService;

  @Autowired
  private EmailConfigRepository emailConfigRepository;
  @Autowired
  private UserRepository userRepository;

  /**
   * 작성자 : 김남주
   * 메서드 기능 : 인증코드 일치 확인
   * 마지막 작성자 : 김남주
   *
   * @param emailAuthCodeMap OR Map // Map 으로 받을지 다른걸로 받을지 확인해봐야함.
   *                         EmailConfigEntity에 email값과 authCode 두개가 같이 없음
   *                         {
   *                         "email":"testidi21233@gmail.com",
   *                         "authCode":"865027"
   *                         }
   * @return String "코드 인증 확인" OR  "코드 불일치"
   */
  @PostMapping("/check")
  public ResponseEntity<?> checkEmail(@RequestBody Map emailAuthCodeMap) {
    if (emailAuthCodeMap == null) {
      log.warn("emailAuthCodeMap 널 값");
      return null;
    }
    boolean checkEmail = emailConfigService.checkEmail(emailAuthCodeMap);
    UserEntity username = userRepository.findByUsername((String) emailAuthCodeMap.get("username"));

    if (checkEmail) {
      UserEntity findUser = userRepository.findByUsername((String) emailAuthCodeMap.get("username"));
      EmailConfigEntity emailConfig = emailConfigRepository.findById(findUser.getId()).orElseThrow(() ->
              new IllegalArgumentException("해당 아이디가 없습니다."));
      emailConfig.setCheck(true);
      emailConfigRepository.save(emailConfig);

      return ResponseEntity.ok().body("코드 인증 확인");
    }
    return ResponseEntity.badRequest().body("코드 인증 불일치");
  }

  /**
   * 작성자 : 김남주
   * 메서드 기능 : 회원 가입한 사람의 이메일을 받아서 인증 코드 전송
   *
   * @param email http://localhost:8080/email/testidi21233@gmail.com
   * @return String 전송 완료 or 전송 실패
   */
  @GetMapping("/{email}")
  public ResponseEntity<?> emailSender(@PathVariable String email) {
    try {
      // uri의 이메일 주소를 서비스의 mailsender 메소드 전달 및 호출
      emailConfigService.mailsend(email);
    } catch (Exception e) {
      log.error("emailSender={}", e.getMessage());
      return ResponseEntity.badRequest().body("전송 실패");
    }
    return ResponseEntity.ok().body("전송 완료");
  }
}