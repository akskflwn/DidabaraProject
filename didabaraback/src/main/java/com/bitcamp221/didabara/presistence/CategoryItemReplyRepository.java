package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CategoryItemReplyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemReplyRepository extends JpaRepository<CategoryItemReplyEntity, Long> {

  @Query("SELECT r FROM CategoryItemReplyEntity r WHERE r.categoryItem = :categoryItemId")
  List<CategoryItemReplyEntity> findList(@Param(value = "categoryItemId") final Long categoryItemId);

  @Query("SELECT r FROM CategoryItemReplyEntity r WHERE r.writer = :userId")
  List<CategoryItemReplyEntity> findMyList(@Param(value = "userId") final Long userId);

  @Query("SELECT r.writer FROM CategoryItemReplyEntity r WHERE r.id = :itemReplyId")
  Long findWriter(@Param(value = "itemReplyId") final Long itemReplyId);

  @Query("SELECT r.categoryItem FROM CategoryItemReplyEntity r WHERE r.id = :itemReplyId")
  Long findCategoryItemId(@Param(value = "itemReplyId") final Long itemReplyId);
}
