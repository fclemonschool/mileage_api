package com.example.mileage.service.impl;

import com.example.mileage.mapper.MileageHistoryMapper;
import com.example.mileage.model.dto.MileageHistoryRequest;
import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.HistoryType;
import com.example.mileage.model.vo.MileageHistoryResponse;
import com.example.mileage.repository.mongo.MileageHistoryRepository;
import com.example.mileage.service.MileageHistoryService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 마일리지 변경이력 서비스 구현체.
 */
@Service
public class MileageHistoryServiceImpl implements MileageHistoryService {

  private final MileageHistoryRepository mileageHistoryRepository;

  public MileageHistoryServiceImpl(MileageHistoryRepository mileageHistoryRepository) {
    this.mileageHistoryRepository = mileageHistoryRepository;
  }

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
  @Transactional
  @Override
  public MileageHistoryResponse create(HistoryType historyType, ActionType actionType, int point,
                                       UUID userId, UUID reviewId) {
    return MileageHistoryMapper.INSTANCE.toVo(mileageHistoryRepository.save(
        MileageHistoryMapper.INSTANCE.toEntity(
            MileageHistoryRequest.builder().historyType(historyType).actionType(actionType)
                .userId(userId).reviewId(reviewId).point(point)
                .build())));
  }
}
