package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.model.CategoryItemEntity;
import com.bitcamp221.didabara.presistence.CategoryItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CategoryItemService {

  @Autowired
  private CategoryItemRepository categoryItemRepository;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 받아온 Entity에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  private void validate(final CategoryItemEntity categoryItemEntity) {
    if (categoryItemEntity == null) {
      log.error("categoryEntity is null");

      throw new RuntimeException("categoryEntity is null");
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 받아온 id에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  private void validateId(final Long id) {
    if (id == null) {
      log.error("id is null");

      throw new RuntimeException("id is null");
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : CategoryItem 생성
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<CategoryItemEntity> create(final CategoryItemEntity categoryItemEntity) {
    validate(categoryItemEntity);

    categoryItemRepository.save(categoryItemEntity);

    log.info("categoryItem entity create success : {}", categoryItemEntity.getId());

    return findCategoryItemList(categoryItemEntity.getCategory());
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : DB로부터 받아온 Entity에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<CategoryItemEntity> findCategoryItemList (final Long categoryId) {
    validateId(categoryId);

    log.info("categoryItem entity create success : {}", categoryId);

    return categoryItemRepository.findCategoryItemList(categoryId);
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


  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : DB로부터 받아온 Entity에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------






}
