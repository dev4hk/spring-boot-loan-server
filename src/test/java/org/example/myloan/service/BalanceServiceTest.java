package org.example.myloan.service;

import org.example.myloan.domain.Application;
import org.example.myloan.domain.Balance;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.BalanceDto;
import org.example.myloan.dto.BalanceDto.Request;
import org.example.myloan.dto.BalanceDto.Response;
import org.example.myloan.dto.BalanceDto.UpdateRequest;
import org.example.myloan.repository.BalanceRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @InjectMocks
    BalanceServiceImpl balanceService;

    @Mock
    BalanceRepository balanceRepository;

    @Spy
    ModelMapper modelMapper;

    @DisplayName("Create Balance")
    @Test
    void Should_ReturnResponseOfNewBalanceEntity_When_RequestCreateBalance() {
        Long applicationId = 1L;
        Request request = Request.builder().entryAmount(BigDecimal.valueOf(100.00)).build();
        when(balanceRepository.findByApplicationId(applicationId)).thenReturn(Optional.ofNullable(null));
        Response actual = balanceService.create(applicationId, request);
        assertThat(actual).isNotNull();
        assertThat(actual.getApplicationId()).isEqualTo(applicationId);
        assertThat(actual.getBalance()).isEqualTo(BigDecimal.valueOf(100.00));
    }

    @DisplayName("Update Balance")
    @Test
    void Should_ReturnUpdatedResponseOfExistBalanceEntity_When_RequestUpdateExistBalanceInfo() {
        Long applicationId = 1L;
        UpdateRequest request = UpdateRequest.builder().beforeEntryAmount(BigDecimal.valueOf(100.00)).afterEntryAmount(BigDecimal.valueOf(200.00)).build();
        Balance balance = Balance.builder().applicationId(applicationId).balance(BigDecimal.valueOf(500.00)).build();
        when(balanceRepository.findByApplicationId(applicationId)).thenReturn(Optional.ofNullable(balance));
        Response actual = balanceService.update(applicationId, request);
        assertThat(actual).isNotNull();
        assertThat(actual.getApplicationId()).isEqualTo(applicationId);
        assertThat(actual.getBalance()).isEqualTo(BigDecimal.valueOf(600.00));
    }


}
