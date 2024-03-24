package org.example.myloan.service;

import org.example.myloan.domain.Application;
import org.example.myloan.domain.Entry;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.EntryDto;
import org.example.myloan.dto.EntryDto.Request;
import org.example.myloan.dto.EntryDto.Response;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.EntryRepository;
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
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntryServiceImplTest {


    @InjectMocks
    EntryServiceImpl entryService;

    @Mock
    EntryRepository entryRepository;

    @Mock
    ApplicationRepository applicationRepository;


    @Spy
    ModelMapper modelMapper;

    @DisplayName("Create Entry")
    @Test
    void Should_ReturnResponseOfNewEntryEntity_When_RequestCreateEntry() {
        Request request = Request.builder().entryAmount(BigDecimal.valueOf(1000.00)).build();
        Application application = Application
                .builder().
                contractedAt(LocalDateTime.now())
                .build();
        Long applicationId = 1L;
        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
        doReturn(null).when(entryRepository).save(any(Entry.class));
        Response actual = entryService.create(applicationId, request);
        assertThat(actual).isNotNull();
        assertThat(actual.getEntryAmount()).isEqualTo(request.getEntryAmount());
    }
}