package com.example.mileage.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.example.mileage.model.dto.MileageRequest;
import com.example.mileage.model.enums.ActionType;
import com.example.mileage.model.enums.MileageType;
import com.example.mileage.service.MileageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MileageController.class)
@AutoConfigureMockMvc
class MileageControllerTest {

  @MockBean
  MileageService mileageService;

  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  public void initAll() {
    this.objectMapper = new ObjectMapper();
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void doEvents() throws Exception {
    // given
    List<UUID> photoList = new ArrayList<>();
    photoList.add(UUID.randomUUID());
    photoList.add(UUID.randomUUID());
    MileageRequest request =
        MileageRequest.builder().type(MileageType.REVIEW).action(ActionType.ADD)
            .reviewId(UUID.randomUUID()).content("좋아요!").attachedPhotoIds(photoList)
            .userId(UUID.randomUUID())
            .placeId(UUID.randomUUID())
            .build();

    // then
    mockMvc.perform(
        post("/api/v1/mileages/events").content(objectMapper.writeValueAsString(request))
            .contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }

  @Test
  void doEventsFail() throws Exception {
    // given
    List<UUID> photoList = new ArrayList<>();
    photoList.add(UUID.randomUUID());
    photoList.add(UUID.randomUUID());
    MileageRequest request =
        MileageRequest.builder().type(MileageType.REVIEW)
            .reviewId(UUID.randomUUID()).content("좋아요!").attachedPhotoIds(photoList)
            .userId(UUID.randomUUID())
            .placeId(UUID.randomUUID())
            .build();

    // then
    mockMvc.perform(
        post("/api/v1/mileages/events").content(objectMapper.writeValueAsString(request))
            .contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
  }

  @Test
  void retrieve() throws Exception {
    // then
    mockMvc.perform(
        get("/api/v1/mileages/{id}", UUID.randomUUID()).contentType(
            MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }
}
