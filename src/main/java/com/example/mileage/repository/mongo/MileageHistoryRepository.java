package com.example.mileage.repository.mongo;

import com.example.mileage.model.document.MileageHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * CRUD 등의 쿼리를 처리하는 마일리지 변경이력 Mongo용 Repository.
 */
public interface MileageHistoryRepository extends MongoRepository<MileageHistory, Long> {
}
