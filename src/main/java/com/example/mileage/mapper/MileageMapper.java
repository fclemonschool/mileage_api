package com.example.mileage.mapper;

import com.example.mileage.model.dto.MileageRequest;
import com.example.mileage.model.entity.Mileage;
import com.example.mileage.model.vo.MileageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct 라이브러리를 통해 Dto, Entity, Vo 사이의 변환을 처리한다.
 */
@Mapper(componentModel = "spring")
public interface MileageMapper extends EntityMapper<MileageRequest, MileageResponse, Mileage> {
  MileageMapper INSTANCE = Mappers.getMapper(MileageMapper.class);
}
