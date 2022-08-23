package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.CategoryEntity;
import com.bitcamp221.didabara.model.CategoryItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.IntBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItemDTO {

  private Long id;
  private Long category;
  private String itemPath;
  private String title;
  private String content;
  private LocalDate expiredDate;
  private LocalDate createdDate;
  private LocalDate modifiedDate;

  public CategoryItemDTO(CategoryItemEntity categoryItemEntity) {
    this.id = categoryItemEntity.getId();
    this.category = categoryItemEntity.getCategory();
    this.itemPath = categoryItemEntity.getItemPath();
    this.title = categoryItemEntity.getTitle();
    this.content = categoryItemEntity.getContent();
    this.expiredDate = categoryItemEntity.getExpiredDate();
    this.createdDate = categoryItemEntity.getCreatedDate();
    this.modifiedDate = categoryItemEntity.getModifiedDate();
  }

  public CategoryItemDTO(Map map, int year, int month, int day) {
    this.category = Long.valueOf(String.valueOf(map.get("categoryId")));
    this.itemPath = String.valueOf(map.get("itemPath"));
    this.content = String.valueOf(map.get("content"));
    this.title = String.valueOf(map.get("title"));
    this.expiredDate = LocalDate.of(year, month, day);
  }

  public static CategoryItemEntity toCategoryItemEntity(final CategoryItemDTO categoryItemDTO) {

    return CategoryItemEntity.builder()
            .id(categoryItemDTO.getId())
            .category(categoryItemDTO.getCategory())
            .itemPath(categoryItemDTO.getItemPath())
            .title(categoryItemDTO.getTitle())
            .content(categoryItemDTO.getContent())
            .expiredDate(categoryItemDTO.getExpiredDate())
            .build();
  }
}