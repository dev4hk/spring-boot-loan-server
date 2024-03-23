package org.example.myloan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.JudgmentDto;
import org.example.myloan.dto.JudgmentDto.Request;
import org.example.myloan.dto.JudgmentDto.Response;
import org.example.myloan.service.JudgmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test - Judgment Controller")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(JudgmentController.class)
class JudgmentControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private JudgmentService judgmentService;

    public JudgmentControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[API][POST] Create an Judgment")
    @Test
    public void createApplication() throws Exception {
        Request request = Request.builder().build();
        Response response = Response.builder().build();
        given(judgmentService.create(any(Request.class)))
                .willReturn(response);
        mvc.perform(post("/judgments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
        then(judgmentService).should().create(any(Request.class));
    }

    @DisplayName("[API][GET] Get an Judgment by JudgmentId")
    @Test
    public void getJudgmentByJudgmentId() throws Exception {
        Long judgmentId = 1L;
        Response response = Response.builder().build();
        given(judgmentService.create(any(Request.class)))
                .willReturn(response);
        mvc.perform(get("/judgments/{judgmentId}", judgmentId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        then(judgmentService).should().get(judgmentId);
    }

    @DisplayName("[API][GET] Get an Judgment by ApplicationId")
    @Test
    public void getJudgmentByApplicationId() throws Exception {
        Long applicationId = 1L;
        Response response = Response.builder().build();
        given(judgmentService.create(any(Request.class)))
                .willReturn(response);
        mvc.perform(get("/judgments/applications/{applicationId}", applicationId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        then(judgmentService).should().getJudgmentOfApplication(applicationId);
    }

    @DisplayName("[API][PUT] Update a Judgment")
    @Test
    public void updateJudgment() throws Exception {
        Long judgmentId = 1L;
        Request request = Request.builder().build();
        Response response = Response.builder().build();
        given(judgmentService.update(judgmentId, request))
                .willReturn(response);
        mvc.perform(put("/judgments/{judgmentId}", judgmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
        then(judgmentService).should().update(eq(judgmentId), any(Request.class));
    }

    @DisplayName("[API][DELETE] Delete a Judgment")
    @Test
    public void deleteJudgment() throws Exception {
        Long judgmentId = 1L;
        willDoNothing().given(judgmentService).delete(judgmentId);
        mvc.perform(delete("/judgments/{judgmentId}", judgmentId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        then(judgmentService).should().delete(judgmentId);
    }
}