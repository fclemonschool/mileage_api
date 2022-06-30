package com.example.mileage.model.document;

import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * DB 시퀀스 Document.
 */
@Document(collection = "database_sequences")
@Getter
@Setter
@EqualsAndHashCode
public class DatabaseSequence {

  @Id
  private String id;

  private long seq;
}
