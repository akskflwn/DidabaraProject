package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.SubscriberDTO;
import com.bitcamp221.didabara.model.SubscriberEntity;
import com.bitcamp221.didabara.service.CategoryService;
import com.bitcamp221.didabara.service.SubscriberService;
import com.bitcamp221.didabara.util.ChangeType;
import com.bitcamp221.didabara.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

  @Autowired
  private SubscriberService subscriberService;

  @Autowired
  private CategoryService categoryService;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Subscriber 생성
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id)
//  -----------------------------------------------------
  @PostMapping("/create/{categoryId}")
  public void create(@AuthenticationPrincipal final String userId,
                     @PathVariable(value = "categoryId", required = false) final Long categoryId) {
    final String message = "subscriber create";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryId != null) {
        SubscriberDTO subscriberDTO = new SubscriberDTO();

        subscriberDTO.setCategory(categoryId);
        subscriberDTO.setUser(Long.valueOf(userId));

        subscriberService.create(SubscriberDTO.toEntity(subscriberDTO));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Subscriber 삭제
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id)
//  -----------------------------------------------------
  @DeleteMapping("/delete/{categoryId}")
  public void delete(@AuthenticationPrincipal final String userId,
                     @PathVariable(value = "categoryId", required = false) final Long categoryId) {
    final String message = "subscriber delete";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryId != null) {
        subscriberService.deleteByCategoryIdAndUserId(categoryId, Long.valueOf(userId));

        log.info(LogMessage.infoComplete(message));
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Subscriber 리스트 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id)
//  -----------------------------------------------------
  @GetMapping("/list/{categoryId}")
  public ResponseEntity<?> list(@AuthenticationPrincipal final String userId,
                                @PathVariable(value = "categoryId", required = false) final Long categoryId) {
    final String message = "subscriber list";

    try {
      log.info(LogMessage.errorJoin(message));

      if (!subscriberService.existsByCategoryIdAndUserId(categoryId, Long.valueOf(userId)) && userId != null && categoryId != null) {

        List<SubscriberEntity> subscriberEntities = subscriberService.findList(categoryId);

        return ChangeType.toSubscriberDTO(subscriberEntities);
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }
}
