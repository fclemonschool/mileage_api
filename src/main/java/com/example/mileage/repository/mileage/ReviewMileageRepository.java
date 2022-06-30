package com.example.mileage.repository.mileage;

import com.example.mileage.model.entity.ReviewMileage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA를 통한 CRUD 등의 쿼리를 처리하는 리뷰-마일리지 Repository.
 */
public interface ReviewMileageRepository
    extends JpaRepository<ReviewMileage, ReviewMileage.ReviewMileageId> {

  /**
   * placeId와 일치하는 경우가 존재하는 지 반환한다.
   *
   * @param placeId 장소 id
   * @return 존재 여부
   */
  boolean existsById_PlaceId(UUID placeId);
}
