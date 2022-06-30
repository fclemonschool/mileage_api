package com.example.mileage.model.dto;

import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.HistoryType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 마일리지 변경 이력에 대한 Dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MileageHistoryRequest {

  private long id;

  private UUID userId;

  private UUID reviewId;

  private int point;

  private ActionType actionType;

  private HistoryType historyType;
}
