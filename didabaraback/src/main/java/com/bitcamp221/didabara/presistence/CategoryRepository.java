package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CategoryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  @Query("SELECT c FROM CategoryEntity c WHERE c.host = :host")
  List<CategoryEntity> findMyList(@Param("host") final Long host);

  @Query("SELECT c FROM CategoryEntity c WHERE c.id = :id")
  CategoryEntity findCategory(@Param("id") final Long id);

  @Query("SELECT c.host FROM CategoryEntity c WHERE c.id = :id")
  Long findHost(@Param("id") final Long id);

  @Query("SELECT c.host FROM  CategoryEntity c WHERE c.id IN (SELECT ci.category FROM CategoryItemEntity ci WHERE ci.id = :itemId)")
  Long findCategoryHost(@Param("itemId") final Long itemId);
}
