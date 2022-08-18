package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckedRepository extends JpaRepository<ReplyEntity, Long> {
}
