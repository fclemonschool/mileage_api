package com.example.mileage.model.dto;

import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.MileageType;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 마일리지 Dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MileageRequest {

  @NotNull(message = "이벤트 타입이 입력되지 않거나 잘못되었습니다.")
  private MileageType type;

  @NotNull(message = "액션이 입력되지 않거나 잘못되었습니다.")
  private ActionType action;

  @NotNull(message = "리뷰 아이디는 필수값입니다.")
  private UUID reviewId;

  private String content;

  private List<UUID> attachedPhotoIds;

  @NotNull(message = "사용자 아이디는 필수값입니다.")
  private UUID userId;

  @NotNull(message = "장소 아이디는 필수값입니다.")
  private UUID placeId;
}
