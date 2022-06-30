package com.example.mileage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.mileage.mapper.MileageMapper;
import com.example.mileage.model.dto.MileageRequest;
import com.example.mileage.model.dto.ReviewMileageRequest;
import com.example.mileage.model.entity.Mileage;
import com.example.mileage.model.entity.ReviewMileage.ReviewMileageId;
import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.HistoryType;
import com.example.mileage.model.enums.MileageType;
import com.example.mileage.model.vo.MileageResponse;
import com.example.mileage.model.vo.ReviewMileageResponse;
import com.example.mileage.repository.mileage.MileageRepository;
import com.example.mileage.service.impl.MileageServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MileageServiceTest {

  MileageService mileageService;

  @Mock
  ReviewMileageService reviewMileageService;

  @Mock
  MileageHistoryService mileageHistoryService;

  @Mock
  MileageRepository mileageRepository;

  Mileage mileage;

  MileageRequest request;

  List<UUID> photoList = new ArrayList<>();

  @Spy
  MileageMapper mapper = Mappers.getMapper(MileageMapper.class);

  @BeforeEach
  public void initAll() {
    this.mileageService =
        new MileageServiceImpl(mileageRepository, mileageHistoryService, reviewMileageService);
    photoList.add(UUID.randomUUID());
    photoList.add(UUID.randomUUID());
    request = MileageRequest.builder().type(MileageType.REVIEW).action(ActionType.ADD)
        .reviewId(UUID.randomUUID()).content("좋아요!").attachedPhotoIds(photoList)
        .userId(UUID.randomUUID()).placeId(UUID.randomUUID()).build();
    mileage = mapper.toEntity(request);
  }

  @Test
  void addRequest() {
    // given
    ReviewMileageId reviewMileageId =
        ReviewMileageId.builder().reviewId(request.getReviewId()).placeId(request.getPlaceId())
            .userId(request.getUserId()).build();
    ReviewMileageRequest reviewMileageRequest =
        ReviewMileageRequest.builder().id(reviewMileageId).hasContent(true).hasPhoto(true)
            .firstReview(true).build();
    when(mileageRepository.save(any())).thenReturn(
        Mileage.builder().userId(request.getUserId()).accumulatedPoint(3)
            .build());

    // when
    MileageResponse response = mileageService.request(request);

    // then
    verify(reviewMileageService).existsById(reviewMileageId);
    verify(reviewMileageService).existsByPlaceId(reviewMileageId.getPlaceId());
    verify(mileageHistoryService).create(HistoryType.FIRST, ActionType.ADD, 1, request.getUserId(),
        request.getReviewId());
    verify(mileageHistoryService).create(HistoryType.PHOTO, ActionType.ADD, 1, request.getUserId(),
        request.getReviewId());
    verify(mileageHistoryService).create(HistoryType.CONTENT, ActionType.ADD, 1,
        request.getUserId(), request.getReviewId());
    verify(reviewMileageService).create(reviewMileageRequest);
    verify(mileageRepository).save(mileage);
    assertEquals(3, response.getAccumulatedPoint());
  }

  @Test
  void modRequest() {
    // given
    request = MileageRequest.builder().type(MileageType.REVIEW).action(ActionType.MOD)
        .reviewId(UUID.randomUUID()).content("좋아요!").userId(UUID.randomUUID())
        .placeId(UUID.randomUUID()).build();
    mileage = mapper.toEntity(request);
    ReviewMileageId reviewMileageId =
        ReviewMileageId.builder().reviewId(request.getReviewId()).placeId(request.getPlaceId())
            .userId(request.getUserId()).build();
    ReviewMileageRequest reviewMileageRequest =
        ReviewMileageRequest.builder().id(reviewMileageId).hasContent(true).hasPhoto(false)
            .firstReview(true).build();
    when(reviewMileageService.retrieve(any())).thenReturn(
        ReviewMileageResponse.builder().id(reviewMileageId).firstReview(true).hasPhoto(true)
            .hasContent(true).build());
    when(mileageRepository.save(any())).thenReturn(
        Mileage.builder().userId(request.getUserId()).accumulatedPoint(2)
            .build());

    // when
    MileageResponse response = mileageService.request(request);

    // then
    verify(reviewMileageService).retrieve(reviewMileageId);
    verify(mileageHistoryService).create(HistoryType.PHOTO, ActionType.MOD, -1, request.getUserId(),
        request.getReviewId());
    verify(reviewMileageService).create(reviewMileageRequest);
    verify(mileageRepository).save(mileage);
    assertEquals(2, response.getAccumulatedPoint());
  }

  @Test
  void deleteRequest() {
    // given
    request = MileageRequest.builder().type(MileageType.REVIEW).action(ActionType.DELETE)
        .reviewId(UUID.randomUUID()).userId(UUID.randomUUID()).placeId(UUID.randomUUID()).build();
    mileage = mapper.toEntity(request);
    ReviewMileageId reviewMileageId =
        ReviewMileageId.builder().reviewId(request.getReviewId()).placeId(request.getPlaceId())
            .userId(request.getUserId()).build();
    when(reviewMileageService.retrieve(any())).thenReturn(
        ReviewMileageResponse.builder().id(reviewMileageId).firstReview(true).hasPhoto(true)
            .hasContent(true).build());
    when(mileageRepository.save(any())).thenReturn(
        Mileage.builder().userId(request.getUserId()).accumulatedPoint(0)
            .build());

    // when
    MileageResponse response = mileageService.request(request);

    // then
    verify(reviewMileageService).retrieve(reviewMileageId);
    verify(mileageHistoryService).create(HistoryType.FIRST, ActionType.DELETE, -1,
        request.getUserId(), request.getReviewId());
    verify(mileageHistoryService).create(HistoryType.PHOTO, ActionType.DELETE, -1,
        request.getUserId(), request.getReviewId());
    verify(mileageHistoryService).create(HistoryType.CONTENT, ActionType.DELETE, -1,
        request.getUserId(), request.getReviewId());
    verify(reviewMileageService).delete(reviewMileageId);
    verify(mileageRepository).save(mileage);
    assertEquals(0, response.getAccumulatedPoint());
  }

  @Test
  void retrieve() {
    // given
    Mileage retrieveMileage =
        Mileage.builder().userId(request.getUserId()).accumulatedPoint(3)
            .build();
    when(mileageRepository.findById(any())).thenReturn(Optional.ofNullable(retrieveMileage));

    // when
    MileageResponse response = mileageService.retrieve(request.getUserId());

    // then
    verify(mileageRepository).findById(request.getUserId());
    assertEquals(response, mapper.toVo(retrieveMileage));
  }
}
