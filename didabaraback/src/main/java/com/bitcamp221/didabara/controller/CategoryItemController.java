package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.CategoryItemDTO;
import com.bitcamp221.didabara.model.CategoryItemEntity;
import com.bitcamp221.didabara.service.CategoryItemService;
import com.bitcamp221.didabara.service.CategoryService;
import com.bitcamp221.didabara.util.ChangeType;
import com.bitcamp221.didabara.util.LogMessage;
import com.bitcamp221.didabara.util.UploadFile;
import com.bitcamp221.didabara.websoket.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categoryItem")
public class CategoryItemController {

    @Autowired
    private CategoryItemService categoryItemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ChatService chatService;
    @Autowired
    private UploadFile uploadFile;

    //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : item 생성
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id), categoryItem(title, content, expiredDate), File
//  -----------------------------------------------------
    @PostMapping("/create/page/{categoryId}")
    public ResponseEntity<?> create(@AuthenticationPrincipal final String userId,
                                    @PathVariable(value = "categoryId", required = false) final Long categoryId,
                                    @RequestPart(value = "categoryItemDTO", required = false) final CategoryItemDTO categoryItemDTO,
                                    @RequestPart(value = "file", required = false) final MultipartFile file) {
        final String message = "categoryItem create";

        try {
            log.info(LogMessage.infoJoin(message));

            if (userId != null && Long.valueOf(userId) == categoryService.findHost(categoryId) &&
                    file.getSize() != 0 && categoryItemDTO != null) {

                final String itemPath = uploadFile.uploadCategoryImage(file);

                categoryItemDTO.setItemPath(itemPath);
                categoryItemDTO.setCategory(categoryId);

                final List<CategoryItemEntity> categoryItemEntities = categoryItemService
                        .create(CategoryItemDTO.toEntity(categoryItemDTO));

                log.info(LogMessage.infoComplete(message));

                chatService.createRoom(categoryItemDTO.getTitle());


                return ChangeType.toCategoryItemDTO(categoryItemEntities);
            } else {
                if (userId == null) {
                    log.error(LogMessage.errorNull(message));

                    throw new RuntimeException(LogMessage.errorNull(message));
                } else {
                    log.info(LogMessage.errorMismatch(message));
                    log.info("{}, {}, {}, {}", userId, categoryId, categoryItemDTO, categoryService.findHost(categoryId));


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
//  메소드 정보 : 나의 itemList 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryId(id), categoryItem(categoryId, id)
//  특이 사항 : 잘못된 코드 추후 검증 및 수정 그리고 기능에 대한 변동 필요
//  -----------------------------------------------------
//  @GetMapping("/myList")
//  public ResponseEntity<?> myList(@AuthenticationPrincipal final String userId,
//                                    @RequestParam(value = "categoryId") final Long categoryId) {
//    final String message = "categoryItem myItemList";
//
//    try {
//      log.info(LogMessage.infoJoin(message));
//
//      if (userId != null && Long.valueOf(userId) == categoryService.findHost(categoryId)) {
//        final List<CategoryItemEntity> categoryItemEntities = categoryItemService.findCategoryItemList(categoryId);
//
//        log.info(LogMessage.infoComplete(message));
//
//        return ChangeType.toCategoryItemDTO(categoryItemEntities);
//      } else {
//        if (userId == null) {
//          log.error(LogMessage.errorNull(message));
//
//          throw new RuntimeException(LogMessage.errorNull(message));
//        } else {
//          log.error(LogMessage.errorMismatch(message));
//
//          throw new RuntimeException(LogMessage.errorMismatch(message));
//        }
//      }
//    } catch (Exception e) {
//      log.error(LogMessage.errorJoin(message));
//
//      return ChangeType.toException(e);
//    }
//  }

    //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 특정 category의 Item 리스트 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryId(id)
//  -----------------------------------------------------
    @GetMapping("/list/{categoryId}")
    public ResponseEntity<?> findList(@AuthenticationPrincipal final String userId,
                                      @PathVariable(value = "categoryId", required = false) final Long categoryId) {
        final String message = "categoryItem list";

        try {
            log.info(LogMessage.infoJoin(message));

            if (userId != null && categoryId != null) {
                final List<CategoryItemEntity> categoryItemEntities = categoryItemService.findList(categoryId);

                return ChangeType.toCategoryItemDTO(categoryItemEntities);
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
//  메소드 정보 : categoryItem 삭제
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id), categoryItem(category, id)
//  -----------------------------------------------------
    @DeleteMapping("/delete/item-page/{categoryItemId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal final String userId,
                                    @PathVariable(value = "categoryItemId", required = false) final Long categoryItemId) {
        final String message = "categoryItem delete";

        try {
            log.info(LogMessage.infoJoin(message));

            final Long host = categoryService.findCategoryItemHost(categoryItemId);

            if (userId != null && host == Long.valueOf(userId)) {
                final List<CategoryItemEntity> categoryItemEntities = categoryItemService
                        .deleteById(categoryItemId);

                log.info(LogMessage.infoComplete(message));

                return ChangeType.toCategoryItemDTO(categoryItemEntities);
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
//  메소드 정보 : categoryItem 수정
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id[필수], content, title, expiredDate, itemPath)
//  -----------------------------------------------------
    @PutMapping("/update/page/{categoryId}")
    public ResponseEntity<?> update(@AuthenticationPrincipal final String userId,
                                    @PathVariable(value = "categoryId", required = false) final Long categoryId,
                                    @RequestBody(required = false) final CategoryItemDTO categoryItemDTO) {
        final String message = "categoryItem update";

        try {
            log.info(LogMessage.infoJoin(message));

            final Long host = categoryService.findCategoryItemHost(categoryItemDTO.getId());

            if (userId != null && Long.valueOf(userId) == host) {
                categoryItemDTO.setCategory(categoryId);

                final CategoryItemEntity categoryItemEntity = CategoryItemDTO.toEntity(categoryItemDTO);

                final List<CategoryItemEntity> categoryItemEntities = categoryItemService.update(categoryItemEntity);

                log.info(LogMessage.infoComplete(message));

                return ChangeType.toCategoryItemDTO(categoryItemEntities);
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