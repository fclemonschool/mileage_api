package com.example.mileage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.mileage.model.document.DatabaseSequence;
import com.example.mileage.model.document.MileageHistory;
import com.example.mileage.service.impl.SequenceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;

@ExtendWith(MockitoExtension.class)
class SequenceServiceTest {

  SequenceService sequenceService;

  @Mock
  MongoOperations mongoOperations;

  @BeforeEach
  public void initAll() {
    this.sequenceService = new SequenceServiceImpl(mongoOperations);
  }

  @Test
  void generateSequence() {
    // given
    DatabaseSequence counter = new DatabaseSequence();
    counter.setSeq(1);
    when(mongoOperations.findAndModify(any(), any(), any(), eq(DatabaseSequence.class)))
        .thenReturn(counter);

    // when
    long result = sequenceService.generateSequence(MileageHistory.SEQUENCE_NAME);

    // then
    assertEquals(1, result);
  }
}
