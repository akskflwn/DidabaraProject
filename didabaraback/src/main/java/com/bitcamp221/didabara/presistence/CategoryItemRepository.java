package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CategoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItemEntity, Long> {
}
