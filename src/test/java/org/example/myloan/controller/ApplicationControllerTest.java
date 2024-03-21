package org.example.myloan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
import org.example.myloan.dto.CounselDto;
import org.example.myloan.service.ApplicationService;
import org.example.myloan.service.CounselService;
import org.example.myloan.service.CounselServiceImpl;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test - Application Controller")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private ApplicationService applicationService;

    ApplicationControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[API][POST] Create a Application")
    @Test
    public void createApplication() throws Exception {
        Request request = Request.builder().build();
        Response response = Response.builder().build();
        given(applicationService.create(any(Request.class)))
                .willReturn(response);
        mvc.perform(post("/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
        then(applicationService).should().create(request);
    }
}