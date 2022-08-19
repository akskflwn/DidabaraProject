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
  List<CategoryEntity> findByHostId(@Param("host") Long host);
}
