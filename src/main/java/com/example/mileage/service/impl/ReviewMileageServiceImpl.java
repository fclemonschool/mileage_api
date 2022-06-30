package com.example.mileage.service.impl;

import com.example.mileage.exception.ExceptionType;
import com.example.mileage.mapper.ReviewMileageMapper;
import com.example.mileage.model.dto.ReviewMileageRequest;
import com.example.mileage.model.entity.ReviewMileage;
import com.example.mileage.model.entity.ReviewMileage.ReviewMileageId;
import com.example.mileage.model.vo.ReviewMileageResponse;
import com.example.mileage.repository.mileage.ReviewMileageRepository;
import com.example.mileage.service.ReviewMileageService;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 리뷰-마일리지 서비스 구현체.
 */
@Transactional(readOnly = true)
@Service
public class ReviewMileageServiceImpl implements ReviewMileageService {

  private final ReviewMileageRepository reviewMileageRepository;

  public ReviewMileageServiceImpl(ReviewMileageRepository reviewMileageRepository) {
    this.reviewMileageRepository = reviewMileageRepository;
  }

  /**
   * 새로운 리뷰-마일리지를 생성한다.
   *
   * @param request 리뷰-마일리지 Dto
   * @return 생성한 리뷰-마일리지에 대한 Vo
   */
  @Transactional
  @Override
  public ReviewMileageResponse create(ReviewMileageRequest request) {
    return ReviewMileageMapper.INSTANCE.toVo(
        reviewMileageRepository.save(ReviewMileageMapper.INSTANCE.toEntity(request)));
  }

  /**
   * 입력받은 리뷰-마일리지 id와 같은 Row를 조회한다.
   *
   * @param reviewMileageId 조회할 입력받은 리뷰-마일리지 id
   * @return 조회한 리뷰 마일리지 Vo
   */
  @Override
  public ReviewMileageResponse retrieve(ReviewMileageId reviewMileageId) {
    Optional<ReviewMileage> reviewMileage = reviewMileageRepository.findById(reviewMileageId);
    if (reviewMileage.isPresent()) {
      return ReviewMileageMapper.INSTANCE.toVo(reviewMileage.get());
    }
    throw new NoSuchElementException(ExceptionType.NO_SUCH_ELEMENT_EXCEPTION.getMessage());
  }

  /**
   * 입력받은 리뷰-마일리지 id와 같은 Row의 존재여부를 반환한다.
   *
   * @param reviewMileageId 존재 여부를 확인할 리뷰-마일리지 id
   * @return 존재 여부
   */
  @Override
  public boolean existsById(ReviewMileageId reviewMileageId) {
    return reviewMileageRepository.existsById(reviewMileageId);
  }

  /**
   * 입력받은 장소 id와 같은 Row의 존재여부를 반환한다.
   *
   * @param placeId 존재 여부를 확인할 장소 id
   * @return 존재 여부
   */
  @Override
  public boolean existsByPlaceId(UUID placeId) {
    return reviewMileageRepository.existsById_PlaceId(placeId);
  }

  /**
   * 입력받은 리뷰-마일리지 아이디와 같은 Row를 삭제한다.
   *
   * @param reviewMileageId 삭제할 리뷰-마일리지 아이디
   */
  @Transactional
  @Override
  public void delete(ReviewMileageId reviewMileageId) {
    reviewMileageRepository.deleteById(reviewMileageId);
  }
}
