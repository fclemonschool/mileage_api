package com.example.mileage.model.vo;

import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.HistoryType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 마일리지 변경 이력 Vo.
 */
@Getter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MileageHistoryResponse {

  private long id;

  private UUID userId;

  private UUID reviewId;

  private int point;

  private ActionType actionType;

  private HistoryType historyType;
}
