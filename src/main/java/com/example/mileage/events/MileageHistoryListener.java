package com.example.mileage.events;

import com.example.mileage.model.document.MileageHistory;
import com.example.mileage.service.SequenceService;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * 마일리지 변경이력에 대한 MongoEventListener.
 */
@Component
public class MileageHistoryListener extends AbstractMongoEventListener<MileageHistory> {

  private final SequenceService sequenceService;

  public MileageHistoryListener(SequenceService sequenceService) {
    this.sequenceService = sequenceService;
  }

  /**
   * 생성 시점과 시퀀스를 통한 id 설정.
   *
   * @param event never {@literal null}.
   */
  @Override
  public void onBeforeConvert(BeforeConvertEvent<MileageHistory> event) {
    if (event.getSource().getId() < 1) {
      event.getSource().setId(sequenceService.generateSequence(MileageHistory.SEQUENCE_NAME));
      event.getSource().setCreatedAt(new Date());
    }
  }
}
