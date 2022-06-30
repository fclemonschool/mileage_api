package com.example.mileage.service;

import com.example.mileage.model.dto.ReviewMileageRequest;
import com.example.mileage.model.entity.ReviewMileage.ReviewMileageId;
import com.example.mileage.model.vo.ReviewMileageResponse;
import java.util.UUID;

/**
 * 리뷰-마일리지 서비스.
 */
public interface ReviewMileageService {

  /**
   * 새로운 리뷰-마일리지를 생성한다.
   *
   * @param request 리뷰-마일리지 Dto
   * @return 생성한 리뷰-마일리지에 대한 Vo
   */
  ReviewMileageResponse create(ReviewMileageRequest request);

  /**
   * 입력받은 리뷰-마일리지 id와 같은 Row를 조회한다.
   *
   * @param reviewMileageId 조회할 입력받은 리뷰-마일리지 id
   * @return 조회한 리뷰 마일리지 Vo
   */
  ReviewMileageResponse retrieve(ReviewMileageId reviewMileageId);

  /**
   * 입력받은 리뷰-마일리지 id와 같은 Row의 존재여부를 반환한다.
   *
   * @param reviewMileageId 존재 여부를 확인할 리뷰-마일리지 id
   * @return 존재 여부
   */
  boolean existsById(ReviewMileageId reviewMileageId);

  /**
   * 입력받은 장소 id와 같은 Row의 존재여부를 반환한다.
   *
   * @param placeId 존재 여부를 확인할 장소 id
   * @return 존재 여부
   */
  boolean existsByPlaceId(UUID placeId);

  /**
   * 입력받은 리뷰-마일리지 아이디와 같은 Row를 삭제한다.
   *
   * @param reviewMileageId 삭제할 리뷰-마일리지 아이디
   */
  void delete(ReviewMileageId reviewMileageId);
}
