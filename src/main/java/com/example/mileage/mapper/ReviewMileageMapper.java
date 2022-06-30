package com.example.mileage.mapper;

import com.example.mileage.model.dto.ReviewMileageRequest;
import com.example.mileage.model.entity.ReviewMileage;
import com.example.mileage.model.vo.ReviewMileageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct 라이브러리를 통해 Dto, Entity, Vo 사이의 변환을 처리한다.
 */
@Mapper(componentModel = "spring")
public interface ReviewMileageMapper
    extends EntityMapper<ReviewMileageRequest, ReviewMileageResponse, ReviewMileage> {
  ReviewMileageMapper INSTANCE = Mappers.getMapper(ReviewMileageMapper.class);
}
