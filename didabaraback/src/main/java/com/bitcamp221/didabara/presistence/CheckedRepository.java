package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CheckedEntity;
import com.bitcamp221.didabara.model.SubscriberEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckedRepository extends JpaRepository<CheckedEntity, Long> {

  @Modifying
  @Query("DELETE FROM CheckedEntity c WHERE c.categoryItem = :categoryItemId")
  void deleteAllByCategoryItem(@Param("categoryItemId") final Long categoryItemId);

  @Query("SELECT c.user FROM CheckedEntity c WHERE c.categoryItem = :categoryItemId")
  List<CheckedEntity> checkUserList(@Param("categoryItemId") final Long categoryItemId);

  @Query("SELECT s.user FROM SubscriberEntity s LEFT JOIN CheckedEntity c WHERE s.category = :categoryItemId AND c.id IS NULL")
  List<SubscriberEntity> unCheckUserList(@Param("categoryItemId") final Long categoryItemId);

  @Query("SELECT c FROM CheckedEntity c WHERE c.user = :userId")
  List<CheckedEntity> findMyCheckList(@Param("userId") final Long userId);

  @Query("SELECT c FROM CheckedEntity c WHERE c.id = :userId")
  List<CheckedEntity> findMyUnCheckList(@Param("userId") final Long userId);

  @Query("SELECT c FROM CheckedEntity c WHERE c.user = : userId")
  boolean existsByUserId (@Param("userId") final Long userId);
}
