package com.example.mileage.model.entity;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

/**
 * 마일리지 Entity.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Mileage {

  @Id
  @Column(columnDefinition = "BINARY(16)", nullable = false)
  private UUID userId;

  private int accumulatedPoint;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Mileage mileage = (Mileage) o;
    return userId != null && Objects.equals(userId, mileage.userId);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
