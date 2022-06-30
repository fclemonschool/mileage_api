package com.example.mileage.mapper;

import com.example.mileage.model.document.MileageHistory;
import com.example.mileage.model.dto.MileageHistoryRequest;
import com.example.mileage.model.vo.MileageHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct 라이브러리를 통해 Dto, Entity, Vo 사이의 변환을 처리한다.
 */
@Mapper(componentModel = "spring")
public interface MileageHistoryMapper
    extends EntityMapper<MileageHistoryRequest, MileageHistoryResponse, MileageHistory> {
  MileageHistoryMapper INSTANCE = Mappers.getMapper(MileageHistoryMapper.class);
}
