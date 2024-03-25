package org.example.myloan.service;

import org.example.myloan.domain.Application;
import org.example.myloan.domain.Entry;
import org.example.myloan.dto.EntryDto.Request;
import org.example.myloan.dto.EntryDto.Response;
import org.example.myloan.dto.EntryDto.UpdateResponse;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.EntryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntryServiceTest {


    @InjectMocks
    EntryServiceImpl entryService;

    @Mock
    EntryRepository entryRepository;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    BalanceServiceImpl balanceService;


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

    @DisplayName("Get Entry")
    @Test
    void Should_ReturnResponseOfExistEntryEntity_When_RequestExistApplicationId() {
        Long applicationId = 1L;
        when(entryRepository.findByApplicationId(applicationId))
                .thenReturn(Optional.of(Entry.builder().applicationId(applicationId).build()));
        Response actual = entryService.get(applicationId);
        assertThat(actual).isNotNull();
        assertThat(actual.getApplicationId()).isEqualTo(applicationId);
    }

    @DisplayName("Update Entry")
    @Test
    void Should_ReturnUpdatedResponseOfExistEntryEntity_When_RequestUpdateExistEntry() {
        Long entryId = 1L;
        Long applicationId = 1L;
        Entry entry = Entry.builder().applicationId(applicationId).entryId(entryId).entryAmount(BigDecimal.valueOf(50.00)).build();
        Request request = Request.builder().entryAmount(BigDecimal.valueOf(100.00)).build();
        when(entryRepository.findById(entryId))
                .thenReturn(Optional.of(entry));
        UpdateResponse actual = entryService.update(entryId, request);
        assertThat(actual).isNotNull();
        assertThat(actual.getEntryId()).isEqualTo(entryId);
        assertThat(actual.getApplicationId()).isEqualTo(applicationId);
        assertThat(actual.getBeforeEntryAmount()).isEqualTo(BigDecimal.valueOf(50.00));
        assertThat(actual.getAfterEntryAmount()).isEqualTo(BigDecimal.valueOf(100.00));
    }

    @DisplayName("Delete Entry")
    @Test
    void Should_DeleteEntryEntity_When_RequestDeleteExistEntry() {
        Long entryId = 1L;
        Entry entry = Entry.builder()
                .entryId(1L)
                .build();
        when(entryRepository.findById(entryId)).thenReturn(Optional.of(entry));
        entryService.delete(entryId);
        assertThat(entry.getIsDeleted()).isSameAs(true);
    }
}