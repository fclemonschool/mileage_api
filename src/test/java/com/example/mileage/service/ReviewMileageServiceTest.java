package com.example.mileage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.mileage.mapper.ReviewMileageMapper;
import com.example.mileage.model.dto.ReviewMileageRequest;
import com.example.mileage.model.entity.ReviewMileage;
import com.example.mileage.model.entity.ReviewMileage.ReviewMileageId;
import com.example.mileage.model.vo.ReviewMileageResponse;
import com.example.mileage.repository.mileage.ReviewMileageRepository;
import com.example.mileage.service.impl.ReviewMileageServiceImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
class ReviewMileageServiceTest {

  ReviewMileageService reviewMileageService;

  @Mock
  ReviewMileageRepository reviewMileageRepository;

  ReviewMileage reviewMileage;

  ReviewMileageId reviewMileageId;

  ReviewMileageRequest request;

  @Spy
  ReviewMileageMapper mapper = Mappers.getMapper(ReviewMileageMapper.class);

  @BeforeEach
  public void initAll() {
    this.reviewMileageService = new ReviewMileageServiceImpl(reviewMileageRepository);
    RequestContextHolder.setRequestAttributes(
        new ServletRequestAttributes(new MockHttpServletRequest()));
    reviewMileageId =
        ReviewMileageId.builder().reviewId(UUID.randomUUID()).userId(UUID.randomUUID())
            .placeId(UUID.randomUUID()).build();
    request = ReviewMileageRequest.builder().firstReview(true).hasContent(true).hasPhoto(true)
        .id(reviewMileageId).build();
    reviewMileage = mapper.toEntity(request);
  }

  @Test
  void create() {
    // given
    when(reviewMileageRepository.save(any())).thenReturn(reviewMileage);

    // when
    ReviewMileageResponse response = reviewMileageService.create(request);

    // then
    verify(reviewMileageRepository).save(reviewMileage);
    assertEquals(response, mapper.toVo(reviewMileage));

  }

  @Test
  void retrieve() {
    // given
    when(reviewMileageRepository.findById(any())).thenReturn(Optional.ofNullable(reviewMileage));

    // when
    ReviewMileageResponse response = reviewMileageService.retrieve(reviewMileageId);

    // then
    verify(reviewMileageRepository).findById(reviewMileageId);
    assertEquals(response, mapper.toVo(reviewMileage));
  }

  @Test
  void existsById() {
    // given
    when(reviewMileageRepository.existsById(any())).thenReturn(true);

    // when
    boolean response = reviewMileageService.existsById(reviewMileageId);

    // then
    verify(reviewMileageRepository).existsById(reviewMileageId);
    assertTrue(response);
  }

  @Test
  void existsByPlaceId() {
    // given
    when(reviewMileageRepository.existsById_PlaceId(any())).thenReturn(true);

    // when
    boolean response = reviewMileageService.existsByPlaceId(reviewMileageId.getPlaceId());

    // then
    verify(reviewMileageRepository).existsById_PlaceId(reviewMileageId.getPlaceId());
    assertTrue(response);
  }

  @Test
  void delete() {
    // when
    reviewMileageService.delete(reviewMileageId);

    // then
    verify(reviewMileageRepository).deleteById(reviewMileageId);
  }
}
