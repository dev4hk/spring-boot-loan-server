package org.example.myloan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.myloan.dto.CounselDto;
import org.example.myloan.dto.TermsDto.Request;
import org.example.myloan.dto.TermsDto.Response;
import org.example.myloan.service.TermsService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test - Terms Controller")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(TermsController.class)
class TermsControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private TermsService termsService;

    public TermsControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[API][POST] Create Terms")
    @Test
    public void createCounsel() throws Exception {
        Request request = Request.builder().build();
        Response response = Response.builder().build();
        given(termsService.create(any(Request.class)))
                .willReturn(response);
        mvc.perform(post("/terms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
        then(termsService).should().create(any(Request.class));
    }

    @DisplayName("[API][GET] Get Terms List")
    @Test
    public void getTermsList() throws Exception {
        given(termsService.getAll())
                .willReturn(List.of());
        mvc.perform(get("/terms"))
                .andExpect(status().isOk());
        then(termsService).should().getAll();
    }
}