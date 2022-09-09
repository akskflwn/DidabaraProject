package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.dto.UserUserInfoDTO;
import com.bitcamp221.didabara.mapper.UserInfoMapper;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class UserInfoService {

  @Autowired
  private UserInfoMapper userInfoMapper;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  UserInfoRepository userInfoRepository;

  @Autowired
  private UserRepository userRepository;

  public int updateMyPage(String id, UserUserInfoDTO uid) {

    UserUserInfoDTO byDTO = userInfoMapper.findByDTO(id);

    if (uid.getNickname() == null) {
      uid.setNickname(byDTO.getNickname());
    }

    if (uid.getJob() == null) {
      uid.setJob(byDTO.getJob());
    }

    if (uid.getPassword() == null) {
      uid.setPassword(byDTO.getPassword());
    }

    String encode = passwordEncoder.encode(uid.getPassword());

    uid.setPassword(encode);

    return userInfoMapper.updateUserInfoDTO(id, uid);
  }

  /**
   * 작성자 : 김남주
   * 메서드 기능 : 내 정보 보기
   * 마지막 작성자 : 김남주
   *
   * @param id
   * @return Map
   */
  public Map findByIdMyPage(Long id) {

    Map byMap = userInfoMapper.findByMap(id);

    return byMap;
  }


  public int delete(String id) throws Exception {
    int checkRow = userInfoMapper.deleteUserAndInfo(id);
    log.info("checkRow={}", checkRow);
    if (checkRow != 0) {
      return checkRow;
    } else {
      throw new Exception("삭제 실패");
    }
  }

  public boolean checkAndChange(UserEntity byId, Map map, PasswordEncoder passwordEncoder) throws Exception {

    String currentPassword = (String) map.get("currentPassword");
    System.out.println("currentPassword = " + currentPassword);
    if (passwordEncoder.matches(currentPassword, byId.getPassword())) {
      String changePassword = (String) map.get("password");
      System.out.println("changePassword = " + changePassword);
      String encodePassword = passwordEncoder.encode(changePassword);
      byId.setPassword(encodePassword);

      userRepository.save(byId);

      return true;
    } else {
      throw new Exception("패스워드가 일치하지 않습니다.");
    }
  }
}