package com.example.mileage.service.impl;

import static com.example.mileage.model.entity.ReviewMileage.ReviewMileageId;

import com.example.mileage.exception.CustomException;
import com.example.mileage.exception.ExceptionType;
import com.example.mileage.mapper.MileageMapper;
import com.example.mileage.model.dto.MileageRequest;
import com.example.mileage.model.dto.ReviewMileageRequest;
import com.example.mileage.model.entity.Mileage;
import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.HistoryType;
import com.example.mileage.model.vo.MileageResponse;
import com.example.mileage.model.vo.ReviewMileageResponse;
import com.example.mileage.repository.mileage.MileageRepository;
import com.example.mileage.service.MileageHistoryService;
import com.example.mileage.service.MileageService;
import com.example.mileage.service.ReviewMileageService;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 마일리지 서비스 구현체.
 */
@Service
@Transactional(readOnly = true)
public class MileageServiceImpl implements MileageService {

  private final MileageRepository mileageRepository;

  private final MileageHistoryService mileageHistoryService;

  private final ReviewMileageService reviewMileageService;

  /**
   * 생성자.
   *
   * @param mileageRepository     mileageRepository
   * @param mileageHistoryService mileageHistoryService
   * @param reviewMileageService  reviewMileageService
   */
  public MileageServiceImpl(MileageRepository mileageRepository,
                            MileageHistoryService mileageHistoryService,
                            ReviewMileageService reviewMileageService) {
    this.mileageRepository = mileageRepository;
    this.mileageHistoryService = mileageHistoryService;
    this.reviewMileageService = reviewMileageService;
  }

  /**
   * 마일리지 등록, 수정, 삭제를 처리하기 위한 요청을 받는다.
   *
   * @param request 마일리지 Dto
   * @return 마일리지 Vo
   */
  @Transactional
  @Override
  public MileageResponse request(MileageRequest request) {
    switch (request.getAction()) {
      case ADD:
        return create(request);
      case MOD:
        return update(request);
      case DELETE:
        return delete(request);
      default:
        throw new IllegalStateException("Unexpected value: " + request.getAction());
    }
  }

  /**
   * 사용자의 마일리지를 조회한다.
   *
   * @param userId 조회할 사용자 id
   * @return 마일리지 Vo
   */
  @Override
  public MileageResponse retrieve(UUID userId) {
    Optional<Mileage> response = mileageRepository.findById(userId);


    if (response.isPresent()) {
      return MileageMapper.INSTANCE.toVo(response.get());
    }
    throw new NoSuchElementException(ExceptionType.NO_SUCH_ELEMENT_EXCEPTION.getMessage());
  }

  /**
   * 사용자의 마일리지를 등록한다.
   *
   * @param request 마일리지 Dto
   * @return 등록한 마일리지 Vo
   */
  private MileageResponse create(MileageRequest request) {
    return MileageMapper.INSTANCE.toVo(mileageRepository.save(calculateCreatePoint(request)));
  }

  /**
   * 마일리지 등록에 대한 마일리지 계산 및 변경 이력을 반영한다.
   *
   * @param request 마일리지 Dto
   * @return 마일리지 Entity
   */
  private Mileage calculateCreatePoint(MileageRequest request) {
    ReviewMileageId key =
        ReviewMileageId.builder().reviewId(request.getReviewId()).placeId(request.getPlaceId())
            .userId(request.getUserId()).build();
    boolean alreadyReviewed = reviewMileageService.existsById(key);
    int currentPoint = 0;
    if (alreadyReviewed) {
      throw new CustomException(ExceptionType.ALREADY_EXISTS_EXCEPTION);
    }
    boolean isFirstReview = !reviewMileageService.existsByPlaceId(request.getPlaceId());
    boolean hasPhoto = request.getAttachedPhotoIds() != null
        && !request.getAttachedPhotoIds().isEmpty();
    boolean hasContent = request.getContent() != null && !request.getContent().isEmpty();

    currentPoint =
        getCurrentPoint(isFirstReview, HistoryType.FIRST, ActionType.ADD, request, 1, currentPoint);

    currentPoint =
        getCurrentPoint(hasPhoto, HistoryType.PHOTO, ActionType.ADD, request, 1, currentPoint);

    currentPoint =
        getCurrentPoint(hasContent, HistoryType.CONTENT, ActionType.ADD, request, 1, currentPoint);

    Mileage entity = calculateAccumulatedPoint(request, currentPoint);
    reviewMileageService.create(
        ReviewMileageRequest.builder().id(key).firstReview(isFirstReview).hasContent(hasContent)
            .hasPhoto(hasPhoto).build());
    return entity;
  }

  /**
   * 마일리지 변경 이력에 대한 등록 및 포인트를 계산한다.
   *
   * @param condition    조건
   * @param historyType  변경 이력 타입
   * @param actionType   액션 타입
   * @param request      마일리지 Dto
   * @param point        변동 포인트
   * @param currentPoint 반영할 포인트
   * @return 반영할 포인트
   */
  private int getCurrentPoint(boolean condition, HistoryType historyType, ActionType actionType,
                              MileageRequest request, int point, int currentPoint) {
    addMileageHistory(condition, historyType, actionType, request, point);
    currentPoint = calculateCurrentPoint(condition, point, currentPoint);
    return currentPoint;
  }

  /**
   * 변동 포인트와 반영할 포인트를 통해서 반영할 포인트를 계산한다.
   *
   * @param condition    조건
   * @param point        변동 포인트
   * @param currentPoint 반영할 포인트
   * @return 반영할 포인트
   */
  private int calculateCurrentPoint(boolean condition, int point, int currentPoint) {
    if (condition) {
      currentPoint = currentPoint + point;
    }
    return currentPoint;
  }

  /**
   * 마일리지 변경 이력을 등록한다.
   *
   * @param condition   조건
   * @param historyType 변경 이력 타입
   * @param actionType  액션 타입
   * @param request     마일리지 Dto
   * @param point       변동 포인트
   */
  private void addMileageHistory(boolean condition, HistoryType historyType, ActionType actionType,
                                 MileageRequest request, int point) {
    if (condition) {
      mileageHistoryService.create(historyType, actionType, point, request.getUserId(),
          request.getReviewId());
    }
  }

  /**
   * 마일리지를 수정한다.
   *
   * @param request 마일리지 Dto
   * @return 수정한 마일리지 Vo
   */
  private MileageResponse update(MileageRequest request) {
    return MileageMapper.INSTANCE.toVo(mileageRepository.save(calculateUpdatePoint(request)));
  }

  /**
   * 마일리지 수정에 대한 마일리지 계산 및 변경 이력을 반영한다.
   *
   * @param request 마일리지 Dto
   * @return 마일리지 Entity
   */
  private Mileage calculateUpdatePoint(MileageRequest request) {
    ReviewMileageId key =
        ReviewMileageId.builder().reviewId(request.getReviewId()).placeId(request.getPlaceId())
            .userId(request.getUserId()).build();
    ReviewMileageResponse reviewMileage = reviewMileageService.retrieve(key);
    int currentPoint = 0;
    boolean hasPhoto = request.getAttachedPhotoIds() != null
        && !request.getAttachedPhotoIds().isEmpty();
    boolean hasContent = request.getContent() != null && !request.getContent().isEmpty();

    currentPoint =
        getCurrentPoint(reviewMileage.isHasPhoto() && !hasPhoto, HistoryType.PHOTO, ActionType.MOD,
            request, -1, currentPoint);
    currentPoint =
        getCurrentPoint(!reviewMileage.isHasPhoto() && hasPhoto, HistoryType.PHOTO, ActionType.MOD,
            request, 1, currentPoint);

    currentPoint = getCurrentPoint(reviewMileage.isHasContent() && !hasContent, HistoryType.CONTENT,
        ActionType.MOD, request, -1, currentPoint);
    currentPoint = getCurrentPoint(!reviewMileage.isHasContent() && hasContent, HistoryType.CONTENT,
        ActionType.MOD, request, 1, currentPoint);

    Mileage entity = calculateAccumulatedPoint(request, currentPoint);
    reviewMileageService.create(
        ReviewMileageRequest.builder().id(key).firstReview(reviewMileage.isFirstReview())
            .hasContent(hasContent).hasPhoto(hasPhoto).build());
    return entity;
  }

  /**
   * 누적 포인트를 계산한다.
   *
   * @param request      마일리지 Dto
   * @param currentPoint 반영할 포인트
   * @return 누적 포인트를 계산한 마일리지 Entity
   */
  private Mileage calculateAccumulatedPoint(MileageRequest request, int currentPoint) {
    Mileage entity = MileageMapper.INSTANCE.toEntity(request);
    if (mileageRepository.existsById(request.getUserId())) {
      entity.setAccumulatedPoint(
          retrieve(request.getUserId()).getAccumulatedPoint() + currentPoint);
    } else {
      entity.setAccumulatedPoint(currentPoint);
    }
    return entity;
  }

  /**
   * 마일리지를 삭제한다.
   *
   * @param request 마일리지 Dto
   * @return 삭제하고 반영된 사용자 마일리지 Vo
   */
  private MileageResponse delete(MileageRequest request) {
    return MileageMapper.INSTANCE.toVo(mileageRepository.save(calculateDeletePoint(request)));
  }

  /**
   * 마일리지 삭제에 대한 마일리지 계산 및 변경 이력을 반영한다.
   *
   * @param request 마일리지 Dto
   * @return 마일리지 Entity
   */
  private Mileage calculateDeletePoint(MileageRequest request) {
    ReviewMileageId key =
        ReviewMileageId.builder().reviewId(request.getReviewId()).placeId(request.getPlaceId())
            .userId(request.getUserId()).build();
    ReviewMileageResponse reviewMileage = reviewMileageService.retrieve(key);
    int currentPoint = 0;

    currentPoint =
        getCurrentPoint(reviewMileage.isFirstReview(), HistoryType.FIRST, ActionType.DELETE,
            request, -1, currentPoint);

    currentPoint =
        getCurrentPoint(reviewMileage.isHasPhoto(), HistoryType.PHOTO, ActionType.DELETE, request,
            -1, currentPoint);

    currentPoint =
        getCurrentPoint(reviewMileage.isHasContent(), HistoryType.CONTENT, ActionType.DELETE,
            request, -1, currentPoint);

    Mileage entity = calculateAccumulatedPoint(request, currentPoint);
    reviewMileageService.delete(key);
    return entity;
  }
}
