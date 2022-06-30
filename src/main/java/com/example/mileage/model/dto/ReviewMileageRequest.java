package com.example.mileage.model.dto;

import com.example.mileage.model.entity.ReviewMileage.ReviewMileageId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 리뷰-마일리지 에 대한 Dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMileageRequest {

  private ReviewMileageId id;

  private boolean firstReview;

  private boolean hasPhoto;

  private boolean hasContent;
}
