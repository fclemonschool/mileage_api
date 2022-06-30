package com.example.mileage.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

/**
 * Event에서 받는 액션 타입.
 */
public enum ActionType {

  ADD, MOD, DELETE;

  /**
   * ActionType에 없는 값이면 null return.
   *
   * @param value 체크할 값
   * @return 값에 해당하는 ActionType
   */
  @JsonCreator
  public static ActionType forValue(String value) {
    return Stream.of(ActionType.values())
        .filter(enumValue -> enumValue.name().equals(value.toUpperCase()))
        .findFirst()
        .orElse(null);
  }
}
