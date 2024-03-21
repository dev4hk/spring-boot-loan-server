package org.example.myloan.service;

import org.example.myloan.domain.Application;
import org.example.myloan.domain.Counsel;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
import org.example.myloan.dto.CounselDto;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.ApplicationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {
    @InjectMocks
    ApplicationServiceImpl applicationService;
    @Mock
    ApplicationRepository applicationRepository;
    @Spy
    ModelMapper modelMapper;

    @DisplayName("Create Application")
    @Test
    void Should_ReturnResponseOfNewApplicationEntity_When_RequestCreateApplication() {
        Application entity = Application.builder()
                .name("Member Kim")
                .cellPhone("123-456-7890")
                .email("mail@abcd.efg")
                .hopeAmount(BigDecimal.valueOf(50000))
                .build();

        Request request = Request.builder()
                .name("Member Kim")
                .cellPhone("123-456-7890")
                .email("mail@abcd.efg")
                .hopeAmount(BigDecimal.valueOf(50000))
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
        Response actual = applicationService.create(request);
        assertThat(actual).isNotNull();
        assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
        assertThat(actual.getName()).isSameAs(entity.getName());
    }

    @DisplayName("Get An Application by ApplicationId")
    @Test
    void Should_ReturnResponseOfExistApplicationEntity_When_RequestExistApplicationId() {
        Long findId = 1L;
        Application entity = Application.builder()
                .applicationId(1L)
                .build();
        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));
        Response actual = applicationService.get(findId);
        assertThat(actual).isNotNull();
        assertThat(actual.getApplicationId()).isSameAs(findId);
    }

    @DisplayName("Get non-existing Application by id")
    @Test
    void Should_ThrowException_When_RequestNonExistApplicationId() {
        Long applicationId = 1L;
        when(applicationRepository.findById(applicationId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));
        assertThrows(BaseException.class, () -> applicationService.get(applicationId));
    }

    @DisplayName("Update an Application")
    @Test
    void Should_ReturnUpdatedResponseOfExistApplicationEntity_When_RequestUpdateExistApplicationInfo() {
        Long applicationId = 1L;
        Application entity = Application.builder()
                .applicationId(1L)
                .hopeAmount(BigDecimal.valueOf(50000))
                .build();

        Request request = Request.builder()
                .hopeAmount(BigDecimal.valueOf(60000))
                .build();

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(entity));

        Response actual = applicationService.update(applicationId, request);
        assertThat(actual).isNotNull();
        assertThat(actual.getApplicationId()).isSameAs(applicationId);
        assertThat(actual.getName()).isSameAs(request.getName());

    }

    @DisplayName("Update non-existing Application")
    @Test
    void Should_ReturnUpdatedResponseOfExistApplicationEntity_When_RequestUpdateNonExistApplicationInfo() {
        Long applicationId = 1L;
        Request request = Request.builder().build();
        when(applicationRepository.findById(applicationId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));
        assertThrows(BaseException.class, () -> applicationService.update(applicationId, request));
    }


}