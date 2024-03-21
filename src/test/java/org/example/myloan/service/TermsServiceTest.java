package org.example.myloan.service;

import org.example.myloan.domain.Terms;
import org.example.myloan.dto.TermsDto;
import org.example.myloan.dto.TermsDto.Request;
import org.example.myloan.repository.TermsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TermsServiceTest {

    @InjectMocks
    TermsServiceImpl termsService;

    @Mock
    TermsRepository termsRepository;

    @Spy
    ModelMapper modelMapper;

    @DisplayName("Create/Add Terms")
    @Test
    void Should_ReturnResponseOfNewTermsEntity_When_RequestTerms() {
        Terms entity = Terms.builder()
                .name("Loan Terms")
                .termsDetailUrl("https://randomTermsPage.")
                .build();
        Request request = Request.builder()
                .name("Loan Terms")
                .termsDetailUrl("https://randomTermsPage.")
                .build();
        when(termsRepository.save(any(Terms.class))).thenReturn(entity);
        TermsDto.Response actual = termsService.create(request);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isSameAs(entity.getName());
        assertThat(actual.getTermsDetailUrl()).isSameAs(entity.getTermsDetailUrl());
    }

}
