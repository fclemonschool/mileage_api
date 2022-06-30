package com.example.mileage.repository.mileage;

import com.example.mileage.model.entity.Mileage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA를 통한 CRUD 등의 쿼리를 처리하는 마일리지 Repository.
 */
public interface MileageRepository extends JpaRepository<Mileage, UUID> {
}
