package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.ReportEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

  @Query("SELECT r FROM ReportEntity r WHERE r.id = :id")
  ReportEntity findReport(@Param("id") final Long id);

  @Query("SELECT r FROM ReportEntity r WHERE r.writer = :user")
  List<ReportEntity> findMyList(@Param("user") final Long user);

  @Query("SELECT r.writer FROM ReportEntity r WHERE r.id = :reportId")
  Long findWriter(@Param("reportId") final Long reportId);
}
