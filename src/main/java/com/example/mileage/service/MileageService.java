package com.example.mileage.service;

import com.example.mileage.model.dto.MileageRequest;
import com.example.mileage.model.vo.MileageResponse;
import java.util.UUID;

/**
 * 마일리지 서비스.
 */
public interface MileageService {

  /**
   * 마일리지 등록, 수정, 삭제를 처리하기 위한 요청을 받는다.
   *
   * @param request 마일리지 Dto
   * @return 마일리지 Vo
   */
  MileageResponse request(MileageRequest request);

  /**
   * 사용자의 마일리지를 조회한다.
   *
   * @param userId 조회할 사용자 id
   * @return 마일리지 Vo
   */
  MileageResponse retrieve(UUID userId);
}
