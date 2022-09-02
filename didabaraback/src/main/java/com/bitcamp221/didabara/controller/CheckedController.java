package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.CheckedDTO;
import com.bitcamp221.didabara.model.CheckedEntity;
import com.bitcamp221.didabara.model.SubscriberEntity;
import com.bitcamp221.didabara.service.CategoryService;
import com.bitcamp221.didabara.service.CheckedService;
import com.bitcamp221.didabara.util.ChangeType;
import com.bitcamp221.didabara.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.nullability.AlwaysNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/checked")
public class CheckedController {

  @Autowired
  private CheckedService checkedService;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Check 생성
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
  @PostMapping("/create")
  public void create(@AuthenticationPrincipal final String userId,
                     @RequestParam(value = "categoryItemId", required = false) final Long categoryItemId) {
    final String message = "checked create";

    try {
      log.info(LogMessage.infoJoin(message));

      if (!checkedService.existsByUserId(Long.valueOf(userId)) && userId != null && categoryItemId != null) {
        CheckedDTO checkedDTO = new CheckedDTO(Long.valueOf(userId), categoryItemId);

        log.info(LogMessage.infoComplete(message));

        checkedService.create(CheckedDTO.toEntity(checkedDTO));
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
//  메소드 정보 : Check 삭제
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
  @DeleteMapping("/delete")
  public void delete(@AuthenticationPrincipal final String userId,
                     @RequestParam(value = "categoryItemId", required = false) final Long categoryItemId) {
    final String message = "checked delete";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryItemId != null) {
        checkedService.delete(categoryItemId);

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
//  메소드 정보 : Check 리스트 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
  @GetMapping("/checkUserList")
  public ResponseEntity<?> checkUserList(@AuthenticationPrincipal final String userId,
                                         @RequestParam(value = "categoryItemId", required = false) final Long categoryItemId) {
    final String message = "checked checkUserList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryItemId != null) {
        log.info(LogMessage.infoComplete(message));

        List<CheckedEntity> checkedEntities = checkedService.findCheckUserList(categoryItemId);

        return ChangeType.toCheckedDTO(checkedEntities);
      } else {
        log.error(LogMessage.errorJoin(message));

        throw new RuntimeException(LogMessage.errorJoin(message));
      }

    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : unCheck 리스트 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
  @GetMapping("/unCheckUserList")
  public ResponseEntity<?> unCheckUserList(@AuthenticationPrincipal final String userId,
                                           @RequestParam(value = "categoryItemId", required = false) final Long categoryItemId) {
    final String message = "checked unCheckUserList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryItemId != null) {

        List<SubscriberEntity> checkedEntities = checkedService.findUnCheckUserList(categoryItemId);

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toSubscriberDTO(checkedEntities);
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 나의 Check 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/myCheckList")
  public ResponseEntity<?> MyCheckList(@AuthenticationPrincipal final String userId) {
    final String message = "checked MyCheckList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        List<CheckedEntity> checkedEntities = checkedService.findMyCheckList(Long.valueOf(userId));

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toCheckedDTO(checkedEntities);
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 나의 unCheck 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/myUnCheckList")
  public ResponseEntity<?> myUnCheckList(@AuthenticationPrincipal final String userId) {
    final String message = "checked unMyCheckList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        List<CheckedEntity> checkedEntities = checkedService.findMyUnCheckList(Long.valueOf(userId));

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toCheckedDTO(checkedEntities);
      } else {
        log.info(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }
}
