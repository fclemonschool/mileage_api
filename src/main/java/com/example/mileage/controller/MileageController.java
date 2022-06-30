package com.example.mileage.controller;

import com.example.mileage.model.dto.MileageRequest;
import com.example.mileage.model.vo.MileageResponse;
import com.example.mileage.service.MileageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 마일리지 컨트롤러.
 */
@Tag(name = "Mileages", description = "마일리지 API")
@RestController
@RequestMapping(value = "/api/v1/mileages")
public class MileageController {

  private final MileageService mileageService;

  public MileageController(MileageService mileageService) {
    this.mileageService = mileageService;
  }

  /**
   * 마일리지 이벤트(등록, 수정, 삭제)에 따라서 마일리지를 계산하여 반영한다.
   *
   * @param request 마일리지 Dto
   * @return 반영된 사용자의 누적 마일리지 점수가 담긴 Vo의 HTTP 형식 응답 객체
   */
  @Operation(summary = "마일리지 이벤트(등록, 수정, 삭제)", description = "마일리지 이벤트에 따라 마일리지를 반영한다.")
  @PostMapping(value = "/events")
  public ResponseEntity<MileageResponse> doEvents(@RequestBody @Valid MileageRequest request) {
    return ResponseEntity.ok(mileageService.request(request));
  }

  /**
   * 사용자의 마일리지 점수를 조회한다.
   *
   * @param userId 사용자 아이디
   * @return 사용자의 누적 마일리지 점수가 담긴 Vo의 HTTP 형식 응답 객체
   */
  @Operation(summary = "사용자 마일리지 조회", description = "사용자의 마일리지를 조회한다.")
  @GetMapping(value = "/{userId}")
  public ResponseEntity<MileageResponse> retrieve(@PathVariable UUID userId) {
    return ResponseEntity.ok(mileageService.retrieve(userId));
  }
}
