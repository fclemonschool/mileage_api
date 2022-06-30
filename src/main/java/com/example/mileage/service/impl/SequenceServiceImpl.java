package com.example.mileage.service.impl;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.example.mileage.model.document.DatabaseSequence;
import com.example.mileage.service.SequenceService;
import java.util.Objects;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * 시퀀스 생성 서비스 구현체.
 */
@Service
public class SequenceServiceImpl implements SequenceService {

  private final MongoOperations mongoOperations;

  public SequenceServiceImpl(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  /**
   * 시퀀스를 생성한다.
   *
   * @param seqName 대상 시퀀스 이름.
   * @return 생성한 시퀀스
   */
  @Override
  public long generateSequence(String seqName) {
    DatabaseSequence counter =
        mongoOperations.findAndModify(query(where("_id").is(seqName)), new Update().inc("seq", 1),
            options().returnNew(true).upsert(true), DatabaseSequence.class);
    return !Objects.isNull(counter) ? counter.getSeq() : 1;
  }
}
