package com.example.mileage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.mileage.mapper.MileageHistoryMapper;
import com.example.mileage.model.document.MileageHistory;
import com.example.mileage.model.dto.MileageHistoryRequest;
import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.HistoryType;
import com.example.mileage.model.vo.MileageHistoryResponse;
import com.example.mileage.repository.mongo.MileageHistoryRepository;
import com.example.mileage.service.impl.MileageHistoryServiceImpl;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
class MileageHistoryServiceTest {

  MileageHistoryService mileageHistoryService;

  @Mock
  MileageHistoryRepository mileageHistoryRepository;

  MileageHistory mileageHistory;

  MileageHistoryRequest request;

  @Spy
  MileageHistoryMapper mapper = Mappers.getMapper(MileageHistoryMapper.class);

  @BeforeEach
  public void initAll() {
    this.mileageHistoryService = new MileageHistoryServiceImpl(mileageHistoryRepository);
    RequestContextHolder.setRequestAttributes(
        new ServletRequestAttributes(new MockHttpServletRequest()));
    request =
        MileageHistoryRequest.builder().historyType(HistoryType.PHOTO).actionType(ActionType.ADD)
            .point(1).reviewId(UUID.randomUUID()).userId(UUID.randomUUID()).build();
    mileageHistory = mapper.toEntity(request);
  }

  @Test
  void create() {
    // given
    when(mileageHistoryRepository.save(any())).thenReturn(mileageHistory);

    // when
    MileageHistoryResponse response =
        mileageHistoryService.create(request.getHistoryType(), request.getActionType(),
            request.getPoint(), request.getUserId(), request.getReviewId());

    // then
    assertEquals(response, mapper.toVo(mileageHistory));
  }
}
