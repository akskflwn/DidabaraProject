package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.model.CategoryEntity;
import com.bitcamp221.didabara.model.EmailConfigEntity;
import com.bitcamp221.didabara.presistence.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : DB로부터 받아온 Entity에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  private void validate(final CategoryEntity categoryEntity) {
    if (categoryEntity == null) {
      log.warn("Error : categoryEntity is null");
      throw new RuntimeException("categoryEntity is null");
    }

    if (categoryEntity.getId() == null) {
      log.warn("Error : categoryId is null");
      throw new RuntimeException("categoryId is null");
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Category 생성
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public CategoryEntity create(final CategoryEntity categoryEntity) {

//    카테고리 유효성 검사 진행
    validate(categoryEntity);

    if (categoryEntity == null) {
      throw new RuntimeException("Categorty Create Failed");
    }

    return categoryRepository.save(categoryEntity);
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : category의 id 값으로 특정 Category 정보 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public Optional<CategoryEntity> find(final Long categoryId) {

    final Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);

    if (categoryEntity != null) {
      return categoryEntity;
    }

    log.warn("find not category");
    return null;
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : user본인이 생성한 Category 전체 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<CategoryEntity> myList(final Long userId) {

    if (userId != null) {
      return categoryRepository.findByHostId(userId);
    }

    return null;
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 특정 Category 수정
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public CategoryEntity update(final CategoryEntity categoryEntity) {

    if (categoryEntity != null) {

      return categoryRepository.save(categoryEntity);
    }

    return null;
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 특정 Category 삭제
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<CategoryEntity> delete(final CategoryEntity categoryEntity) {
    validate(categoryEntity);

    try {
      categoryRepository.delete(categoryEntity);
    } catch (Exception e) {
      log.error("Error category delete", categoryEntity.getId(), e);
      throw new RuntimeException("Error category delete" + categoryEntity.getId());
    }

    return myList(categoryEntity.getHost().getId());
  }
}
