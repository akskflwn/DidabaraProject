package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.CategoryDTO;
import com.bitcamp221.didabara.dto.CategoryItemDTO;
import com.bitcamp221.didabara.dto.ResponseDTO;
import com.bitcamp221.didabara.model.CategoryItemEntity;
import com.bitcamp221.didabara.service.CategoryItemService;
import com.bitcamp221.didabara.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/categoryitem")
//과연 이건 필요한가.... Category에서 /# 으로 id값 열람이 맞지 않는가....
public class CategoryItemController {

  @Autowired
  CategoryItemService categoryItemService;

  @Autowired
  CategoryService categoryService;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : item 생성
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @PostMapping
  public ResponseEntity<?> create(@AuthenticationPrincipal final String userId,
                                  @RequestBody final Map map) {
    try {
      if (userId == null || map == null) {
        log.error("categoryItem DTO or categoryId or userId is null");

        throw new RuntimeException("categoryItem DTO or categoryId or userId is null");
      }

      final Long loginId = Long.valueOf(userId);

      final Long host = categoryService.findHost(Long.valueOf(String.valueOf(map.get("categoryId"))));

      if (loginId == host) {
        log.info("categoryitem create join success : {}", map.get("categoryId"));

        final String[] splitExpiredDate = String.valueOf(map.get("expiredDate")).split("-");

        final int year = Integer.parseInt(splitExpiredDate[0]);
        final int month = Integer.parseInt(splitExpiredDate[1]);
        final int day = Integer.parseInt(splitExpiredDate[2]);

        final CategoryItemDTO itemDTO = new CategoryItemDTO(map, year, month, day);

        final List<CategoryItemEntity> categoryItemEntities = categoryItemService.create
                (CategoryItemDTO.toCategoryItemEntity(itemDTO));

        final List<CategoryItemDTO> categoryItemDTOS = categoryItemEntities.stream().map(CategoryItemDTO::new).collect(Collectors.toList());

        final ResponseDTO<CategoryItemDTO> responseDTO = ResponseDTO.<CategoryItemDTO>builder().resList(categoryItemDTOS).build();

        log.info("categoryItem create success");

        return ResponseEntity.ok().body(responseDTO);
      } else {
        log.info("userId, host mismatch");

        throw new RuntimeException("userId, host mismatch");
      }
    } catch (Exception e) {
      log.error("categoryItem create failed");

      final ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 아이템 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public ResponseEntity<?> itemList(@AuthenticationPrincipal final String userId,
                                    @RequestBody final CategoryDTO categoryDTO) {
    try {
      log.info("itemList join success");

      Long host = Long.valueOf(userId);

      if (host == categoryService.findHost(categoryDTO.getId())) {
        final List<CategoryItemEntity> categoryItemEntities = categoryItemService.findCategoryItemList(categoryDTO.getId());

        final List<CategoryItemDTO> categoryItemDTOS = categoryItemEntities.stream().map(CategoryItemDTO::new).collect(Collectors.toList());

        final ResponseDTO<CategoryItemDTO> responseDTO = ResponseDTO.<CategoryItemDTO>builder().resList(categoryItemDTOS).build();

        return ResponseEntity.ok().body(responseDTO);
      } else {
        log.info("host, categoryId mismatch");

        throw new RuntimeException("userId, host mismatch");
      }
    } catch (Exception e) {
      log.info("itemList join failed");

      final ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

      return ResponseEntity.badRequest().body(responseDTO);
    }
  }


  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : DB로부터 받아온 Entity에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------


  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : DB로부터 받아온 Entity에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------


  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : DB로부터 받아온 Entity에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------


  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : DB로부터 받아온 Entity에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
}
