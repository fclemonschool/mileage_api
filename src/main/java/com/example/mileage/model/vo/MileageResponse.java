package com.example.mileage.model.vo;

import com.example.mileage.model.enums.MileageType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 마일리지 Vo.
 */
@Getter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MileageResponse {

  private UUID userId;

  private MileageType type;

  private int accumulatedPoint;
}
