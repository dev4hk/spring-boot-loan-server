package org.example.myloan.service;

import org.example.myloan.domain.Application;
import org.example.myloan.domain.Judgment;
import org.example.myloan.domain.Terms;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.GrantAmount;
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
    JudgmentServiceImpl judgmentService;

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

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(Application.builder().build()));
        when(judgmentRepository.save(any(Judgment.class))).thenReturn(judgment);

        Response actual = judgmentService.create(request);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isSameAs(judgment.getName());
        assertThat(actual.getApplicationId()).isSameAs(judgment.getApplicationId());
        assertThat(actual.getApprovalAmount()).isSameAs(judgment.getApprovalAmount());
    }

    @DisplayName("Get Judgment")
    @Test
    void Should_ReturnResponseOfExistJudgmentEntity_When_RequestExistJudgmentId() {
        Judgment judgment = Judgment.builder()
                .applicationId(1L)
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.of(judgment));
        Response actual = judgmentService.get(1L);

        assertThat(actual).isNotNull();
        assertThat(actual.getJudgmentId()).isSameAs(judgment.getJudgmentId());
    }

    @DisplayName("Get Judgment by Application ID")
    @Test
    void Should_ReturnResponseOfExistJudgmentEntity_When_RequestExistApplicationId() {
        Judgment judgment = Judgment.builder()
                .applicationId(1L)
                .build();

        Application application = Application.builder()
                .applicationId(1L)
                .build();
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));
        when(judgmentRepository.findByApplicationId(1L)).thenReturn(Optional.of(judgment));
        Response actual = judgmentService.getJudgmentOfApplication(1L);

        assertThat(actual.getJudgmentId()).isSameAs(judgment.getJudgmentId());
    }

    @DisplayName("Update Judgment")
    @Test
    void Should_ReturnUpdatedResponseOfExistJudgmentEntity_When_RequestUpdateExistJudgmentInfo() {
        Judgment judgment = Judgment.builder()
                .judgmentId(1L)
                .name("Member Kim")
                .approvalAmount(BigDecimal.valueOf(5000))
                .build();

        Request request = Request.builder()
                .name("Member Lee")
                .approvalAmount(BigDecimal.valueOf(10000))
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.of(judgment));

        Response actual = judgmentService.update(1L, request);
        assertThat(actual).isNotNull();
        assertThat(actual.getJudgmentId()).isSameAs(1L);
        assertThat(actual.getName()).isSameAs(request.getName());
        assertThat(actual.getApprovalAmount()).isSameAs(request.getApprovalAmount());
    }

    @DisplayName("Delete Judgment")
    @Test
    void Should_DeletedJudgmentEntity_When_RequestDeleteExistJudgmentInfo() {
        Judgment judgment = Judgment.builder()
                .judgmentId(1L)
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.of(judgment));

        judgmentService.delete(1L);
        assertThat(judgment.getIsDeleted()).isTrue();
    }

    @DisplayName("Update Grant Amount from Judgment to Application")
    @Test
    void Should_ReturnUpdateResponseOfExistApplicationEntity_When_RequestGrantApprovalAmountOfJudgmentInfo() {
        Judgment judgment = Judgment.builder()
                .applicationId(1L)
                .name("Member Kim")
                .approvalAmount(BigDecimal.valueOf(5000))
                .build();

        Application application = Application.builder()
                .applicationId(1L)
                .approvalAmount(BigDecimal.valueOf(5000))
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(judgment));
        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        GrantAmount actual = judgmentService.grant(1L);
        assertThat(actual).isNotNull();
        assertThat(actual.getApplicationId()).isSameAs(1L);
        assertThat(actual.getApprovalAmount()).isSameAs(judgment.getApprovalAmount());
    }

}
