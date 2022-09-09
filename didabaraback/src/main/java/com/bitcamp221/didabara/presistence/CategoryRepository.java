package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CategoryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  List<CategoryEntity> findAllByHost(@Param("host") final Long host);

  @Query("SELECT c.host FROM CategoryEntity c WHERE c.id = :id")
  Long findHost(@Param("id") final Long id);

  @Query("SELECT c.host FROM CategoryEntity c WHERE c.id IN (SELECT ci.category FROM CategoryItemEntity ci WHERE ci.id = :itemId)")
  Long findCategoryHost(@Param("itemId") final Long itemId);

  @Query("SELECT c.id FROM CategoryEntity c WHERE c.inviteCode = :inviteCode")
  Long existsCategory(@Param("inviteCode") final String inviteCode);

  @Query("SELECT c.profileImageUrl FROM CategoryEntity c WHERE c.id = :id")
  String findUrl(@Param("id") final Long id);
}
