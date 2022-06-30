package com.example.mileage.service;

import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.HistoryType;
import com.example.mileage.model.vo.MileageHistoryResponse;
import java.util.UUID;

/**
 * 마일리지 변경이력 서비스.
 */
public interface MileageHistoryService {

  /**
   * 마일리지 변경 이력을 생성한다.
   *
   * @param historyType 변경 이력 타입
   * @param actionType  액션 타입
   * @param point       변동 포인트
   * @param userId      사용자 id
   * @param reviewId    리뷰 id
   * @return 마일리지 변경이력 Vo
   */
  MileageHistoryResponse create(HistoryType historyType, ActionType actionType, int point,
                                UUID userId, UUID reviewId);
}
