package org.example.myloan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.myloan.config.ModelMapperConfig;
import org.example.myloan.dto.CounselDto;
import org.example.myloan.dto.CounselDto.Request;
import org.example.myloan.dto.CounselDto.Response;
import org.example.myloan.service.CounselService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test - Counsel Controller")
@Import(ModelMapperConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(CounselController.class)
class CounselControllerTest {
    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private CounselService counselService;

    public CounselControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[API][POST] Create a Counsel")
    @Test
    public void createCounsel() throws Exception {
        Request counselRequestDto = Request.builder().build();
        Response counselResponseDto = Response.builder().build();
        given(counselService.create(any(Request.class)))
                .willReturn(counselResponseDto);
        mvc.perform(post("/counsels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(counselRequestDto))
        )
                .andExpect(status().isOk());
        then(counselService).should().create(any(Request.class));
    }

    @DisplayName("[API][GET] Get a Counsel by counselId")
    @Test
    public void getCounselById() throws Exception {
        Long counselId = 1L;
        Response counselResponseDto = Response.builder().build();
        given(counselService.create(any(Request.class)))
                .willReturn(counselResponseDto);
        mvc.perform(get("/counsels/{counselId}", counselId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        then(counselService).should().get(counselId);
    }

    @DisplayName("[API][PUT] Update a Counsel")
    @Test
    public void updateCounsel() throws Exception {
        Long counselId = 1L;
        Request counselRequestDto = Request.builder().build();
        Response counselResponseDto = Response.builder().build();
        given(counselService.update(counselId, counselRequestDto))
                .willReturn(counselResponseDto);
        mvc.perform(put("/counsels/{counselId}", counselId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(counselRequestDto))
                )
                .andExpect(status().isOk());
        then(counselService).should().update(counselId, counselRequestDto);
    }
}