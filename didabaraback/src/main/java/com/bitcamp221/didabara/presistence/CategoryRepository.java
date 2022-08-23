package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CategoryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  @Query("SELECT c FROM CategoryEntity c WHERE c.host = :host")
  List<CategoryEntity> findMyList(@Param("host") Long host);

  @Query("SELECT c FROM CategoryEntity c WHERE c.id = :id")
  CategoryEntity findCategory(@Param("id") Long id);

  @Query("SELECT c.host FROM CategoryEntity c WHERE  c.id = :id")
  Long findHost(@Param("id") Long id);
}
