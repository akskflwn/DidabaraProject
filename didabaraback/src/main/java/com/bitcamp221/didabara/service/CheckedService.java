package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.model.CheckedEntity;
import com.bitcamp221.didabara.model.SubscriberEntity;
import com.bitcamp221.didabara.presistence.CheckedRepository;
import com.bitcamp221.didabara.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CheckedService {

  @Autowired
  private CheckedRepository checkedRepository;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 받아온 데이터에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  private void validate(final CheckedEntity checkedEntity, final String message) {
    if (checkedEntity == null) {
      log.error(LogMessage.errorNull(message));

      throw new RuntimeException(LogMessage.errorNull(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 받아온 데이터에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  private void validateId(final Long id, final String message) {
    if (id == null) {
      log.error(LogMessage.errorNull(message));

      throw new RuntimeException(LogMessage.errorNull(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Check 생성
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public void create(final CheckedEntity checkedEntity) {
    final String message = "checkedService create";

    try {
      log.info(LogMessage.infoJoin(message));

      validate(checkedEntity, message);

      checkedRepository.save(checkedEntity);

      log.info(LogMessage.infoComplete(message));
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Check 삭제
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public void delete(final Long categoryItemId) {
    final String message = "checkedService delete";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryItemId, message);

      log.info(LogMessage.infoComplete(message));

      checkedRepository.deleteAllByCategoryItem(categoryItemId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Check 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<CheckedEntity> findCheckUserList(final Long categoryItemId) {
    final String message = "checkedService checkUserList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryItemId, message);

      log.info(LogMessage.infoComplete(message));

      return checkedRepository.checkUserList(categoryItemId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : unCheck 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<SubscriberEntity> findUnCheckUserList(final Long categoryItemId) {
    final String message = "checkedService unCheckUserList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryItemId, message);

      log.info(LogMessage.infoComplete(message));

      return checkedRepository.unCheckUserList(categoryItemId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 나의 Check 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<CheckedEntity> findMyCheckList(final Long userId) {
    final String message = "checkedService MyCheckList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return checkedRepository.findMyCheckList(userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 나의 unCheck 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<CheckedEntity> findMyUnCheckList(final Long userId) {
    final String message = "checkedService findMyUnCheckList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return checkedRepository.findMyUnCheckList(userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public boolean existsByUserId (final Long userId){
    final String message = "checkedService findByUserId";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return checkedRepository.existsByUserId(userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }
}
