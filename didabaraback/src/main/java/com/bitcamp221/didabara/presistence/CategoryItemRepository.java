package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CategoryItemEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItemEntity, Long> {

    @Query("SELECT c FROM CategoryItemEntity c WHERE c.category = :category")
    List<CategoryItemEntity> findCategoryItemList(@Param("category") final Long category);

    @Query("SELECT c.category FROM CategoryItemEntity c WHERE c.id = :categoryItemId")
    Long findCategoryId(@Param(value = "categoryItemId") final Long categoryItemId);
}
