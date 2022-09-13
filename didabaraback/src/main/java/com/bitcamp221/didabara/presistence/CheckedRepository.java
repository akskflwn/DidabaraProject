package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.dto.CheckUserDTO;
import com.bitcamp221.didabara.model.CategoryItemEntity;
import com.bitcamp221.didabara.model.CheckedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckedRepository extends JpaRepository<CheckedEntity, Long> {

  void deleteByCategoryItem(@Param("categoryItemId") final Long categoryItemId);

  String checkUserList = "SELECT new com.bitcamp221.didabara.dto.CheckUserDTO" +
          "(u.nickname, ui.profileImageUrl)" +
          "FROM CheckedEntity c " +
          "INNER JOIN UserEntity u ON c.user = u.id " +
          "INNER JOIN UserInfoEntity ui ON ui.id = u.id " +
          "WHERE c.categoryItem = :categoryItemId AND u.id != :user";

  @Query(value = checkUserList)
  List<CheckUserDTO> findCheckUserList(@Param("categoryItemId") final Long categoryItemId, @Param("user") final Long user);


  String unCheckUserList = "SELECT new com.bitcamp221.didabara.dto.CheckUserDTO(u.nickname, ui.profileImageUrl)" +
          "FROM UserEntity u LEFT OUTER JOIN CheckedEntity ch ON u.id = ch.user INNER JOIN UserInfoEntity ui ON u.id = ui.id " +
          "WHERE u.id != :user AND ch.categoryItem = :categoryItemId";

  @Query(value = unCheckUserList)
  List<CheckUserDTO> findUnCheckUserList(@Param("categoryItemId") final Long categoryItemId, @Param("user") final Long user);

  @Query("SELECT ci.category, ci.title, ci.preview FROM CategoryItemEntity ci LEFT OUTER JOIN CheckedEntity ch " +
          "ON ci.id = ch.categoryItem WHERE ch.user = :user")
  List<CategoryItemEntity> findMyCheckList(@Param("user") final Long user);

  @Query("SELECT ci.category, ci.title, ci.preview FROM SubscriberEntity s INNER JOIN CategoryItemEntity ci ON s.category = ci.id " +
          "LEFT OUTER JOIN CheckedEntity ch ON ch.id IS NULL WHERE s.user = :userId")
  List<CategoryItemEntity> findMyUnCheckList(@Param("userId") final Long userId);

  boolean existsByUser(@Param("user") final Long user);
}
