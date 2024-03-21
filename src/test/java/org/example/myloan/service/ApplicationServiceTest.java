package org.example.myloan.service;

import org.example.myloan.domain.Application;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
}