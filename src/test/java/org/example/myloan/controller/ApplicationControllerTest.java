package org.example.myloan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
import org.example.myloan.dto.CounselDto;
import org.example.myloan.service.ApplicationService;
import org.example.myloan.service.CounselService;
import org.example.myloan.service.CounselServiceImpl;
import org.example.myloan.service.FileStorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test - Application Controller")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private ApplicationService applicationService;

    @MockBean
    private FileStorageService fileStorageService;

    ApplicationControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[API][POST] Create an Application")
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
        then(applicationService).should().create(any(Request.class));
    }

    @DisplayName("[API][GET] Get an Application by ApplicationId")
    @Test
    public void getApplicationById() throws Exception {
        Long applicationId = 1L;
        Response response = Response.builder().build();
        given(applicationService.create(any(Request.class)))
                .willReturn(response);
        mvc.perform(get("/applications/{applicationId}", applicationId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        then(applicationService).should().get(applicationId);
    }

    @DisplayName("[API][PUT] Update an Application")
    @Test
    public void updateApplication() throws Exception {
        Long applicationId = 1L;
        Request request = Request.builder().build();
        Response response = Response.builder().build();
        given(applicationService.update(applicationId, request))
                .willReturn(response);
        mvc.perform(put("/applications/{applicationId}", applicationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
        then(applicationService).should().update(eq(applicationId), any(Request.class));
    }

    @DisplayName("[API][DELETE] Delete an Application")
    @Test
    public void deleteApplication() throws Exception {
        Long applicationId = 1L;
        willDoNothing().given(applicationService).delete(applicationId);
        mvc.perform(delete("/applications/{applicationId}", applicationId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        then(applicationService).should().delete(applicationId);
    }

    @DisplayName("[API][POST] Accept Terms")
    @Test
    public void acceptTerms() throws Exception {
        Long applicationId = 1L;
        ApplicationDto.AcceptTerms terms = ApplicationDto.AcceptTerms.builder().build();
        given(applicationService.acceptTerms(anyLong(), any(ApplicationDto.AcceptTerms.class))).willReturn(true);
        mvc.perform(post("/applications/{applicationId}/terms", applicationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(terms))
                )
                .andExpect(status().isOk());
        then(applicationService).should().acceptTerms(eq(applicationId), any(ApplicationDto.AcceptTerms.class));
    }

    @DisplayName("[API][PUT] Contract an Application")
    @Test
    public void contractApplication() throws Exception {
        Long applicationId = 1L;
        Response response = Response.builder().build();
        given(applicationService.contract(applicationId))
                .willReturn(response);
        mvc.perform(put("/applications/{applicationId}/contract", applicationId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        then(applicationService).should().contract(applicationId);
    }
}
