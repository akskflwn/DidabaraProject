package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.CategoryItemReplyDTO;
import com.bitcamp221.didabara.model.CategoryItemReplyEntity;
import com.bitcamp221.didabara.service.CategoryItemReplyService;
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
@RequestMapping("/categoryItemReply")
public class CategoryItemReplyController {

  @Autowired
  private CategoryItemReplyService itemReplyService;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : itemReplyList 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
  @GetMapping("/list/{categoryItemId}")
  public ResponseEntity<?> findList(@AuthenticationPrincipal final String userId,
                                    @PathVariable(value = "categoryItemId") final Long categoryItemId) {
    final String message = "itemReply itemReplyList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (categoryItemId != null && userId != null) {
        final List<CategoryItemReplyEntity> replyEntities = itemReplyService.findList(categoryItemId);

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toItemReplyDTO(replyEntities);
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
//  메소드 정보 : itemReplyMyList 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/myList")
  public ResponseEntity<?> findMyList(@AuthenticationPrincipal final String userId) {
    final String message = "itemReply myList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        final List<CategoryItemReplyEntity> replyEntities = itemReplyService.findMyList(Long.valueOf(userId));

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toItemReplyDTO(replyEntities);
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
//  메소드 정보 : itemReply 생성
//  마지막 수정자 : 문병훈
//  필요 데이터 : itemReply(content), categoryItem(id)
//  -----------------------------------------------------
  @PostMapping("/create/page/{categoryItemId}")
  public ResponseEntity<?> create(@AuthenticationPrincipal final String userId,
                                  @PathVariable(value = "categoryItemId") final Long categoryItemId,
                                  @RequestBody final String content) {
    final String message = "itemReply create";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && content != null && categoryItemId != null) {
        final CategoryItemReplyDTO itemReplyDTO = new CategoryItemReplyDTO(Long.valueOf(userId),categoryItemId, content);

        final CategoryItemReplyEntity itemReplyEntity = CategoryItemReplyDTO.toEntity(itemReplyDTO);

        final List<CategoryItemReplyEntity> replyEntities = itemReplyService.create(itemReplyEntity);

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toItemReplyDTO(replyEntities);
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
//  메소드 정보 : reply 수정
//  마지막 수정자 : 문병훈
//  필요 데이터 : itemReply(id, content)
//  -----------------------------------------------------
  @PutMapping("/update/page/{categoryItemId}")
  public ResponseEntity<?> update(@AuthenticationPrincipal final String userId,
                                  @PathVariable(value = "categoryItemId") final Long categoryItemId,
                                  @RequestBody final CategoryItemReplyDTO itemReplyDTO) {
    final String message = "itemReply update";

    try {
      log.info(LogMessage.infoJoin(message));

      final Long writer = itemReplyService.findWriter(itemReplyDTO.getId());

      final Long category = itemReplyService.findCategoryId(itemReplyDTO.getId());

      if (userId != null && Long.valueOf(userId) == writer && categoryItemId == category) {
        itemReplyDTO.setCategoryItem(categoryItemId);
        itemReplyDTO.setWriter(Long.valueOf(userId));

        final List<CategoryItemReplyEntity> itemReplyEntity = itemReplyService.update(CategoryItemReplyDTO.toEntity(itemReplyDTO));

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toItemReplyDTO(itemReplyEntity);
      } else {
        if (userId == null) {
          log.error(LogMessage.errorNull(message));

          throw new RuntimeException(LogMessage.errorNull(message));
        } else {
          log.error(LogMessage.errorMismatch(message));

          throw new RuntimeException(LogMessage.errorMismatch(message));
        }
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : reply 삭제
//  마지막 수정자 : 문병훈
//  필요 데이터 : itemReply(id)
//  -----------------------------------------------------
  @DeleteMapping("/delete/{itemReplyId}")
  public ResponseEntity<?> delete(@AuthenticationPrincipal final String userId,
                                  @PathVariable(value = "itemReplyId") final Long itemReplyId) {
    final String message = "itemReply delete";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && Long.valueOf(userId) == itemReplyService.findWriter(itemReplyId)) {
        final List<CategoryItemReplyEntity> replyEntities = itemReplyService.deleteById(itemReplyId);

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toItemReplyDTO(replyEntities);
      } else {
        if (userId == null) {
          log.error(LogMessage.errorNull(message));

          throw new RuntimeException(LogMessage.errorNull(message));
        } else {
          log.error(LogMessage.errorMismatch(message));

          throw new RuntimeException(LogMessage.errorMismatch(message));
        }
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }
}