package com.example.mileage.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

/**
 * 마일리지 타입.
 */
public enum MileageType {

  REVIEW;

  /**
   * MileageType에 없는 값이면 null return.
   *
   * @param value 체크할 값
   * @return 값에 해당하는 MileageType
   */
  @JsonCreator
  public static MileageType forValue(String value) {
    return Stream.of(MileageType.values())
        .filter(enumValue -> enumValue.name().equals(value.toUpperCase()))
        .findFirst()
        .orElse(null);
  }
}
