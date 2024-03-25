package org.example.myloan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.myloan.dto.EntryDto;
import org.example.myloan.dto.EntryDto.Request;
import org.example.myloan.dto.EntryDto.Response;
import org.example.myloan.service.EntryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test - Internal Controller")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(InternalController.class)
class InternalControllerTest {

    private final MockMvc mvc;

    private final ObjectMapper objectMapper;

    @MockBean
    EntryService entryService;

    InternalControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[API][POST] Create an Entry")
    @Test
    public void createEntry() throws Exception {
        Long applicationId = 1L;
        Request request = Request.builder().build();
        Response response = Response.builder().build();
        given(entryService.create(applicationId, request)).willReturn(response);
        mvc.perform(post("/internal/applications/{applicationId}/entries", applicationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isOk());
        then(entryService).should().create(applicationId, request);
    }
}