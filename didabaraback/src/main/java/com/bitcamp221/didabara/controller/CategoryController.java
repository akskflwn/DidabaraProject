<<<<<<< HEAD
//package com.bitcamp221.didabara.controller;
//
//import com.bitcamp221.didabara.dto.CategoryDTO;
//import com.bitcamp221.didabara.dto.CategoryItemDTO;
//import com.bitcamp221.didabara.dto.ResponseDTO;
//import com.bitcamp221.didabara.model.CategoryEntity;
//import com.bitcamp221.didabara.service.CategoryService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//@RequestMapping("/category")
//public class CategoryController {
//
//  @Autowired
//  CategoryService categoryService;
//
//  //  -----------------------------------------------------
////  작성자 : 문병훈
////  메소드 정보 : host의 카테고리 리스트 맵핑
////  마지막 수정자 : 문병훈
////  -----------------------------------------------------
//  @GetMapping("/mylist")
//  public ResponseEntity<?> myList (Long userId) {
//    List<CategoryEntity> categoryEntities = categoryService.myList(userId);
//    List<CategoryDTO> categoryDTOS = categoryEntities.stream().map(CategoryDTO::new).collect(Collectors.toList());
//
//    ResponseDTO<CategoryDTO> responseDTO = ResponseDTO.<CategoryDTO>builder().resList(categoryDTOS).build();
//
//    return ResponseEntity.ok().body(responseDTO);
//  }
//
//  //  -----------------------------------------------------
////  작성자 : 문병훈
////  메소드 정보 : 카테고리 생성 맵핑 (생성 후 카테고리 내부)
////  마지막 수정자 : 문병훈
////  -----------------------------------------------------
//
////  @PostMapping
////  public
//
//  //  -----------------------------------------------------
////  작성자 : 문병훈
////  메소드 정보 : 내 카테고리 수정 (수정 후 카테고리 내부)
////  마지막 수정자 : 문병훈
////  -----------------------------------------------------
//
////  @PostMapping
////  public
//
//  //  -----------------------------------------------------
////  작성자 : 문병훈
////  메소드 정보 : 특정 Category 삭제
////  마지막 수정자 : 문병훈
////  -----------------------------------------------------
//
//  //  -----------------------------------------------------
////  작성자 : 문병훈
////  메소드 정보 : 특정 Category 삭제
////  마지막 수정자 : 문병훈
////  -----------------------------------------------------
//
//
//}
=======
package com.bitcamp221.didabara.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

}
>>>>>>> parent of 678823d (mbh)
