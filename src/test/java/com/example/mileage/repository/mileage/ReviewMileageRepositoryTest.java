package com.example.mileage.repository.mileage;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewMileageRepositoryTest {

  @Autowired
  ReviewMileageRepository reviewMileageRepository;

  @Test
  void existsById_PlaceId() {
    // then
    assertFalse(reviewMileageRepository.existsById_PlaceId(UUID.randomUUID()));
  }
}
