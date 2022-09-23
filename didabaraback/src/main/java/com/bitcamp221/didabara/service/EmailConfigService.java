package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.mapper.EmailConfigMapper;
import com.bitcamp221.didabara.mapper.UserMapper;
import com.bitcamp221.didabara.model.EmailConfigEntity;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.EmailConfigRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class EmailConfigService {

  @Autowired
  private EmailConfigRepository emailConfigRepository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private EmailConfigMapper emailConfigMapper;

  @Autowired
  private UserRepository userRepository;

  private final JavaMailSender mailSender;


  /**
   * 작성자 : 김남주
   * 빨간줄 보이는게 맞습니다.
   *
   * @param mailSender
   */
  @Autowired
  public EmailConfigService(@Lazy JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  /**
   *
   */
  public boolean checkEmail(Map emailAuthCodeMap) {
    Iterator<String> iter = emailAuthCodeMap.keySet().iterator();

    while (iter.hasNext()) {
      String key = iter.next();
      String value = (String) emailAuthCodeMap.get(key);

      System.out.println(key + " : " + value);
    }
    Map haveAuthCodeUser = null;
    try {
      haveAuthCodeUser = userMapper.selectUsernameAndAuthCode(emailAuthCodeMap);
      String o1 = (String) emailAuthCodeMap.get("authCode");
      String o = (String) haveAuthCodeUser.get("auth_code");
      log.info("o1={}", o1);
      log.info("o={}", o);
      if (!o1.equals(o)) {
        throw new Exception("일치하지 않는 계정, 코드");
      }
      return true;
    } catch (Exception e) {
      String message = e.getMessage();
      log.error("checkEmail={}", message);
      return false;
    }
  }


  /**
   *
   */
  public void mailsend(String email) throws Exception {
    // 난수 발생
    String code = UUID.randomUUID().toString().substring(0, 6);
    log.info("uuid={}", code);

    // 이메일로 찾은 유저 객체
    UserEntity userIdByEmail = userMapper.selectUserIdByEmail(email);

    // 이메일로 찾은 유저 객체의 아이디 emailconfig 테이블에 저장
    Long id = userIdByEmail.getId();
    EmailConfigEntity checkEmailEntity = emailConfigMapper.selectEmailConfig(id);
    if (checkEmailEntity == null) {
      emailConfigMapper.saveUserIntoEmailconfig(id, code);
    } else {
      emailConfigMapper.updateUserIntoEmailconfig(id, code);
    }


    MimeMessage m = mailSender.createMimeMessage();
    MimeMessageHelper h = new MimeMessageHelper(m, "UTF-8");
    h.setFrom("akskflwn@naver.com");
    h.setTo(email);
    h.setSubject("인증 메일이 도착했습니다.");
    h.setText(code); // 이메일 본문에 적을 값
    mailSender.send(m);

  }
}