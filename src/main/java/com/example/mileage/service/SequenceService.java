package com.example.mileage.service;

/**
 * 시퀀스 생성 서비스.
 */
public interface SequenceService {

  /**
   * 시퀀스를 생성한다.
   *
   * @param seqName 대상 시퀀스 이름.
   * @return 생성한 시퀀스
   */
  long generateSequence(String seqName);
}
