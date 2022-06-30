package com.example.mileage.model.document;

import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.HistoryType;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 마일리지 변경 이력 Document.
 */
@Document
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MileageHistory {

  @Transient
  public static final String SEQUENCE_NAME = "mileage_history_sequence";

  @Id
  private long id;

  @Column(columnDefinition = "BINARY(16)", nullable = false)
  private UUID userId;

  @Column(columnDefinition = "BINARY(16)", nullable = false)
  private UUID reviewId;

  private int point;

  @Column(nullable = false)
  private ActionType actionType;

  @Column(nullable = false)
  private HistoryType historyType;

  private Date createdAt;
}
