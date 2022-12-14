package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.S3Upload;
import com.bitcamp221.didabara.dto.UserUserInfoDTO;
import com.bitcamp221.didabara.mapper.UserInfoMapper;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/userinfo")
public class UserInfoController {

  private final UserInfoService userInfoService;

  private final UserInfoRepository userInfoRepository;

  private final UserInfoMapper userInfoMapper;

  private final ResourceLoader resourceLoader;


  private final S3Upload s3Upload;


  private final UserRepository userRepository;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @PostMapping("/changepassword")
  public ResponseEntity<?> getMyPassword(@AuthenticationPrincipal String id, @RequestBody Map map) {
    System.out.println("id = " + id);
    System.out.println("map.toString() = " + map.toString());
    UserEntity byId = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalArgumentException("???????????? ????????????."));

    try {
      if (userInfoService.checkAndChange(byId, map, passwordEncoder)) {
        return ResponseEntity.ok().body("???????????? ??????");
      }

    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }


    return ResponseEntity.badRequest().body("???????????? ?????? ??????");
  }

  @GetMapping("/{fileName}")
  public ResponseEntity<Resource> resourceFileDownload(@PathVariable String fileName) {
    try {
      Resource resource = resourceLoader
              .getResource("file:\\C:\\projectbit\\didabara\\didabaraback\\src\\main\\resources\\static\\imgs\\" + fileName);
      File file = resource.getFile();

      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
              .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
              .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
              .body(resource);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(null);
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * ????????? : ?????????
   * ????????? ?????? : admin ???????????? ????????? ??? ??????
   * ????????? ban ?????? true??? fasle??? false??? true
   * ????????? ????????? : ?????????
   *
   * @param id     token user id
   * @param userId ?????? ????????? ????????? ?????????
   * @return UserInfoEntity
   */
  @GetMapping("/admin/{userId}")
  public ResponseEntity<?> adminBan(@AuthenticationPrincipal String id, @PathVariable String userId) {
    // ?????? ??????
    UserInfoEntity admin = userInfoMapper.findByIdInUserInfo(id);
    if (admin.getRole() != 1) {
      return ResponseEntity.badRequest().body("???????????? ????????????.");
    }

    // ??? ??????
    UserInfoEntity byIdInUser = userInfoMapper.findByIdInUserInfo(userId);

    if (byIdInUser.isBan() == false) {
      byIdInUser.setBan(true);
      userInfoMapper.updateBan(byIdInUser);
    } else {
      byIdInUser.setBan(false);
      userInfoMapper.updateBan(byIdInUser);
    }

    return ResponseEntity.ok().body(byIdInUser);
  }


  @PostMapping("/upload")
  public UserInfoEntity upload(@AuthenticationPrincipal String id, @RequestPart MultipartFile files) throws IOException {
    // db??? ????????? ?????? ?????? ??????
    String code = UUID.randomUUID().toString().substring(0, 6);

    // ??????????????? ????????? ????????? ?????? ????????????
    String sourceFileName = files.getOriginalFilename();
    // ????????? ????????? ????????????
    String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

    File destinationFile;
    String destinationFileName;
    String fileUrl = "C:\\projectbit\\didabara\\didabaraback\\src\\main\\resources\\static\\imgs\\";
//    String fileUrl = "https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/";

    do {
      destinationFileName = code + "." + sourceFileNameExtension;
      destinationFile = new File(fileUrl + destinationFileName);
    } while (destinationFile.exists());

    destinationFile.getParentFile().mkdirs();
    files.transferTo(destinationFile);

    Long userid = Long.valueOf(id);


    UserInfoEntity findUser = userInfoRepository.findById(userid).orElseThrow(() ->
            new IllegalArgumentException("?????? ???????????? ????????????."));

    findUser.setFilename(destinationFileName);
    findUser.setProfileImageUrl(fileUrl);
    findUser.setFileOriName(sourceFileName);

    userInfoRepository.save(findUser);
    return findUser;

  }

  // ??? ?????? ??????
  @GetMapping
  public ResponseEntity<?> myPage(@AuthenticationPrincipal String id) {
    log.info("id={}", id);
    Long userid = Long.valueOf(id);

    // id??? ??? ?????? ???????????? (user ????????? user_info ????????? ??????)
    Map byIdMyPage = userInfoService.findByIdMyPage(userid);

    byIdMyPage.put("password", null);


    return ResponseEntity.ok().body(byIdMyPage);
  }

  /**
   * ????????? : ?????????
   * ????????? ?????? : ??????????????? ?????? (?????? ????????????????????? ????????? ?????? ??????????????? ????????????)
   * ????????? ????????? : ?????????
   *
   * @param id  JWT id
   * @param uid ????????????
   * @return
   */
  @PatchMapping
  public ResponseEntity<?> updateMyPage(@AuthenticationPrincipal String id, @RequestBody UserUserInfoDTO uid) {

    String s = uid.toString();
    System.out.println("s = " + s);
    // s = nickname:dwkow job:null password:1111 username:adwddawd realName:dwdadw


    int checkRow = userInfoService.updateMyPage(id, uid);

    System.out.println("checkRow = " + checkRow);

    if (checkRow < 2) {
      return ResponseEntity.badRequest().body("???????????? ??????");
    }

    return ResponseEntity.ok().body(uid);
  }


  /**
   * ????????? : ?????????
   * ????????? ?????? : ?????? ??????
   *
   * @param id
   * @return
   */
  @DeleteMapping
  public ResponseEntity<?> deleteMypage(@AuthenticationPrincipal String id) {

    try {
      int delete = userInfoService.delete(id);
      return ResponseEntity.ok().body(delete);
    } catch (Exception e) {
      String error = e.getMessage();
      return ResponseEntity.badRequest().body(error);
    }

  }


  @PostMapping
  private ResponseEntity<?> uploadText(@RequestParam("images") MultipartFile files,
                                       @AuthenticationPrincipal String id) throws IOException {
    return ResponseEntity.ok().body(s3Upload.upload(files, "myfile", id));
  }

  @PatchMapping("/svg")
  private ResponseEntity<?> uploadSvg(@RequestParam("svgname") String svgName, @AuthenticationPrincipal String id) throws IOException {
    System.out.println("svgName = " + svgName);
    UserInfoEntity byId = userInfoRepository.findById(Long.valueOf(id))
            .orElseThrow(() -> new RuntimeException("?????? ?????????"));
    byId.setProfileImageUrl(byId.getProfileImageUrl());
    byId.setFilename(svgName);

    UserInfoEntity updateUser = userInfoRepository.save(byId);


    return ResponseEntity.ok().body(updateUser);
  }


}