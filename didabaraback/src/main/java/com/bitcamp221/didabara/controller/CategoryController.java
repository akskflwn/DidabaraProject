package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.CategoryDTO;
import com.bitcamp221.didabara.dto.ResponseDTO;
import com.bitcamp221.didabara.model.CategoryEntity;
import com.bitcamp221.didabara.security.TokenProvider;
import com.bitcamp221.didabara.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

  @Autowired
  CategoryService categoryService;

  @Autowired
  TokenProvider tokenProvider;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : host의 카테고리 리스트 맵핑
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/mylist")
  public ResponseEntity<?> myList(@AuthenticationPrincipal String userId) {
    try {

      if (userId == null) {
        log.error("category mylist get userId is null");

        throw new RuntimeException("category mylist get userId is null");
      }

      log.info("mylist join success : {}", userId);

      //      String 타입인 host를 롱 타입으로 변환
      Long id = Long.valueOf(userId);

      final List<CategoryEntity> categoryEntities = categoryService.myList(id);

      final List<CategoryDTO> categoryDTOS = categoryEntities.stream().map(CategoryDTO::new).collect(Collectors.toList());

      final ResponseDTO<CategoryDTO> responseDTO = ResponseDTO.<CategoryDTO>builder().resList(categoryDTOS).build();

      log.info("mylist find success");

      return ResponseEntity.ok().body(responseDTO);
    } catch (Exception e) {
      log.error("category mylist get failed");

      final ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : host의 카테고리 리스트 맵핑
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/{categoryId}")
  public ResponseEntity<?> findCategory(@PathVariable final Long categoryId) {
    try {

      if (categoryId == null) {
        log.error("category find id is null");

        throw new RuntimeException("category find id is null");
      }

      log.info("categoryId join success : {}", categoryId);

      final CategoryEntity categoryEntity = categoryService.findByCategory(categoryId);

      final CategoryDTO categoryDTO = new CategoryDTO(categoryEntity);

      log.info("category find success");

      return ResponseEntity.ok().body(categoryDTO);
    } catch (Exception e) {
      log.error("category find failed");

      final ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 카테고리 생성 맵핑 (생성 후 카테고리 내부)
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @PostMapping
  public ResponseEntity<?> create(@AuthenticationPrincipal final String userId, @RequestBody final CategoryDTO categoryDTO) {
    try {
      log.info("create join success : {}", categoryDTO.getId());

      if (categoryDTO == null) {
        log.error("Category DTO is null");

        throw new RuntimeException("Category DTO is null");
      }

      Long host = Long.valueOf(userId);

//      랜덤 난수 초대 코드 생성.
      String code = UUID.randomUUID().toString().substring(0, 8);

      categoryDTO.setInviteCode(code);
      categoryDTO.setHost(host);

      final CategoryEntity categoryEntity = CategoryDTO.toCategoryEntity(categoryDTO);

      final CategoryEntity entity = categoryService.create(categoryEntity);

      final CategoryDTO DTO = new CategoryDTO(entity);

      log.info("category create success");

      return ResponseEntity.ok().body(DTO);
    } catch (Exception e) {
      log.error("category create failed");

      final ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 내 카테고리 수정 (수정 후 카테고리 내부)
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @PutMapping
  public ResponseEntity<?> update(@AuthenticationPrincipal final String userId, @RequestBody final CategoryDTO categoryDTO) {
    try {

      if (userId == null || categoryDTO == null) {
        log.error("category update id userId or categoryDTO is null");

        throw new RuntimeException("category update id userId or categoryDTO is null");
      }

      if (categoryService.existByCategory(categoryDTO.getId())) {
        log.error("category is not exist");

        throw new RuntimeException("category is not exist");
      }

      //      String 타입인 host를 롱 타입으로 변환
      Long host = Long.valueOf(userId);

      if (host == categoryDTO.getHost()) {

        log.info("category update join success : {}", categoryDTO.getId());

        final CategoryEntity categoryEntity = CategoryDTO.toCategoryEntity(categoryDTO);

        final CategoryEntity entity = categoryService.update(categoryEntity);

        log.info("category update success");

        return ResponseEntity.ok().body(entity);
      } else {
        log.error("userId does not match");

        throw new RuntimeException("userId does not match");
      }
    } catch (Exception e) {
      log.error("category update failed");

      final ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 특정 Category 삭제 (삭제 후 내 리스트 출력)
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @DeleteMapping
  public ResponseEntity<?> delete(@AuthenticationPrincipal final String host, @RequestBody final CategoryDTO categoryDTO) {
    try {
      log.info("category delete join success : {}", categoryDTO);

      if (categoryDTO == null || host == null) {
        log.error("category delete DTO or id is null");

        throw new RuntimeException("category delete DTO or id is null");
      }

      if (categoryService.existByCategory(categoryDTO.getId())) {
        log.error("category is not exist");

        throw new RuntimeException("category is not exist");
      }

//      String 타입인 host를 롱 타입으로 변환
      Long id = Long.valueOf(host);

      if (id == categoryDTO.getHost()) {

        final CategoryEntity categoryEntity = CategoryDTO.toCategoryEntity(categoryDTO);

        final List<CategoryEntity> categoryEntities = categoryService.delete(id, categoryEntity);

        final List<CategoryDTO> categoryDTOS = categoryEntities.stream().map(CategoryDTO::new).collect(Collectors.toList());

        final ResponseDTO<CategoryDTO> responseDTO = ResponseDTO.<CategoryDTO>builder().resList(categoryDTOS).build();

        log.info("category delete success");

        return ResponseEntity.ok().body(responseDTO);
      } else {
        log.error("userId does not match");

        throw new RuntimeException("userId does not match");
      }
    } catch (Exception e) {

      log.error("category delete failed");

      final String error = e.getMessage();

      final ResponseDTO<CategoryDTO> responseDTO = ResponseDTO.<CategoryDTO>builder().error(error).build();

      return ResponseEntity.badRequest().body(responseDTO);
    }
  }
}

