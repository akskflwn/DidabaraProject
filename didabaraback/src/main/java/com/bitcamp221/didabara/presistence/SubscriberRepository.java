package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.SubscriberEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, Long> {

  @Modifying
  @Query("DELETE FROM SubscriberEntity s WHERE s.category = :categoryId AND s.user = :userId")
  void deleteByCategoryIdAndUserId(@Param("categoryId") final Long categoryId, @Param("userId") final Long userId);

  @Query("SELECT s FROM SubscriberEntity s WHERE s.category = :categoryId")
  List<SubscriberEntity> findList(@Param("categoryId") final Long categoryId);

  @Query("SELECT s.id FROM SubscriberEntity s WHERE s.category = :categoryId AND s.user = :userId")
  boolean existsByCategoryIdAndUserId (@Param("categoryId") final Long categoryId, @Param("userId") final Long userId);
}