package org.example.myloan.service;

import org.example.myloan.domain.AcceptTerms;
import org.example.myloan.domain.Application;
import org.example.myloan.domain.Counsel;
import org.example.myloan.domain.Terms;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
import org.example.myloan.dto.CounselDto;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.AcceptTermsRepository;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.TermsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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
    @Mock
    TermsRepository termsRepository;
    @Mock
    AcceptTermsRepository acceptTermsRepository;
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

    @DisplayName("Delete an Application by id")
    @Test
    void Should_DeleteApplicationEntity_When_RequestDeleteExistApplication() {
        Long applicationId = 1L;
        Application entity = Application.builder()
                .applicationId(1L)
                .build();
        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(entity));
        applicationService.delete(applicationId);
        assertThat(entity.getIsDeleted()).isSameAs(true);
    }

    @DisplayName("Delete non-existing Application")
    @Test
    void Should_DeleteApplicationEntity_When_RequestDeleteNonExistApplication() {
        Long applicationId = 1L;
        when(applicationRepository.findById(applicationId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));
        assertThrows(BaseException.class, () -> applicationService.delete(applicationId));
    }

    @DisplayName("Add Accepted Terms")
    @Test
    void Should_AddAcceptTerms_When_RequestAcceptTermsOfApplication() {
        Terms terms1 = Terms.builder()
                .termsId(1L)
                .name("Terms 1")
                .termsDetailUrl("https://testTermsUrl.abc/terms1")
                .build();
        Terms terms2 = Terms.builder()
                .termsId(2L)
                .name("Terms 2")
                .termsDetailUrl("https://testTermsUrl.abc/terms2")
                .build();
        List<Long> acceptTerms = Arrays.asList(1L, 2L);
        ApplicationDto.AcceptTerms request = ApplicationDto.AcceptTerms.builder()
                .acceptTermsIds(acceptTerms)
                .build();
        Long findId = 1L;
        when(applicationRepository.findById(findId))
                .thenReturn(Optional.of(Application.builder().build()));
        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId")))
                .thenReturn(Arrays.asList(terms1, terms2));
        when(acceptTermsRepository.save(any(AcceptTerms.class)))
                .thenReturn(AcceptTerms.builder().build());
        Boolean actual = applicationService.acceptTerms(findId, request);
        assertThat(actual).isNotNull();
        assertThat(actual).isTrue();
    }

    @DisplayName("Throw Exception When Applicant Does Not Accept All Terms")
    @Test
    void Should_ThrowException_When_RequestNotAllAcceptTermsOfApplication() {
        Terms terms1 = Terms.builder()
                .termsId(1L)
                .name("Terms 1")
                .termsDetailUrl("https://testTermsUrl.abc/terms1")
                .build();

        Terms terms2 = Terms.builder()
                .termsId(2L)
                .name("Terms 2")
                .termsDetailUrl("https://testTermsUrl.abc/terms2")
                .build();
        List<Long> acceptTerms = Arrays.asList(1L);
        ApplicationDto.AcceptTerms request = ApplicationDto.AcceptTerms.builder()
                .acceptTermsIds(acceptTerms)
                .build();
        Long findId = 1L;
        when(applicationRepository.findById(findId))
                .thenReturn(Optional.of(Application.builder().build()));
        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId")))
                .thenReturn(Arrays.asList(terms1, terms2));
        assertThrows(BaseException.class, () -> applicationService.acceptTerms(findId, request));

    }

    @DisplayName("Throw Exception When Accepting Non-existing Terms")
    @Test
    void Should_ThrowException_When_RequestNotExistTermsOfApplication() {
        Terms terms1 = Terms.builder()
                .termsId(1L)
                .name("Terms 1")
                .termsDetailUrl("https://testTermsUrl.abc/terms1")
                .build();
        Terms terms2 = Terms.builder()
                .termsId(2L)
                .name("Terms 2")
                .termsDetailUrl("https://testTermsUrl.abc/terms2")
                .build();
        List<Long> acceptTerms = Arrays.asList(1L, 3L);
        ApplicationDto.AcceptTerms request = ApplicationDto.AcceptTerms.builder()
                .acceptTermsIds(acceptTerms)
                .build();
        Long findId = 1L;
        when(applicationRepository.findById(findId))
                .thenReturn(Optional.of(Application.builder().build()));
        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId")))
                .thenReturn(Arrays.asList(terms1, terms2));
        assertThrows(BaseException.class, () -> applicationService.acceptTerms(findId, request));

    }
}
