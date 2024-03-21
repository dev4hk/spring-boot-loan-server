package org.example.myloan.service;

import org.example.myloan.domain.Application;
import org.example.myloan.domain.Judgment;
import org.example.myloan.domain.Terms;
import org.example.myloan.dto.JudgmentDto;
import org.example.myloan.dto.JudgmentDto.Request;
import org.example.myloan.dto.JudgmentDto.Response;
import org.example.myloan.dto.TermsDto;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.JudgmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("")
@ExtendWith(MockitoExtension.class)
class JudgmentServiceTest {

    @InjectMocks
    JudgmentService judgmentService;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    JudgmentRepository judgmentRepository;

    @Spy
    private ModelMapper modelMapper;

    @DisplayName("Create Judgment")
    @Test
    void Should_ReturnResponseOfNewJudgmentEntity_When_RequestNewJudgment() {
        Judgment judgment = Judgment.builder()
                .applicationId(1L)
                .name("Member Kim")
                .approvalAmount(BigDecimal.valueOf(5000))
                .build();

        Request request = Request.builder()
                .applicationId(1L)
                .name("Member Kim")
                .approvalAmount(BigDecimal.valueOf(5000))
                .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().build()));
        when(judgmentRepository.save(any(Judgment.class))).thenReturn(judgment);

        Response actual = judgmentService.create(request);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isSameAs(judgment.getName());
        assertThat(actual.getApplicationId()).isSameAs(judgment.getApplicationId());
        assertThat(actual.getApprovalAmount()).isSameAs(judgment.getApprovalAmount());

    }


}