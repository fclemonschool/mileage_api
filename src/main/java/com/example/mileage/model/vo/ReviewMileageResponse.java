package com.example.mileage.model.vo;

import com.example.mileage.model.entity.ReviewMileage.ReviewMileageId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 리뷰-마일리지 Vo.
 */
@Getter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewMileageResponse {

  private ReviewMileageId id;

  boolean firstReview;

  boolean hasPhoto;

  boolean hasContent;
}
