package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.CategoryItemEntity;
import com.bitcamp221.didabara.model.CheckedEntity;
import com.bitcamp221.didabara.model.SubscriberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedDTO {

  private Long id;
  private SubscriberEntity user;
  private CategoryItemEntity categoryItem;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  public CheckedDTO (CheckedEntity checkedEntity) {
    this.id = checkedEntity.getId();
    this.user = checkedEntity.getUser();
    this.categoryItem = checkedEntity.getCategoryItem();
    this.createdDate = checkedEntity.getCreatedDate();
    this.modifiedDate = checkedEntity.getModifiedDate();
  }
  public static CheckedEntity checkedEntity(final CheckedDTO checkedDTO) {

    return CheckedEntity.builder()
            .id(checkedDTO.getId())
            .user(checkedDTO.getUser())
            .categoryItem(checkedDTO.getCategoryItem())
            .build();
  }
}
