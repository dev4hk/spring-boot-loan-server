package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Balance;
import org.example.myloan.dto.BalanceDto;
import org.example.myloan.dto.BalanceDto.Request;
import org.example.myloan.dto.BalanceDto.Response;
import org.example.myloan.dto.BalanceDto.UpdateRequest;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.BalanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        if(balanceRepository.findByApplicationId(applicationId).isPresent()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Balance balance = modelMapper.map(request, Balance.class);

        BigDecimal entryAmount = request.getEntryAmount();
        balance.setApplicationId(applicationId);
        balance.setBalance(entryAmount);

        balanceRepository.findByApplicationId(applicationId).ifPresent(b -> {
            balance.setBalanceId(b.getBalanceId());
            balance.setIsDeleted(b.getIsDeleted());
            balance.setCreatedAt(b.getCreatedAt());
            balance.setUpdatedAt(b.getUpdatedAt());
        });

        balanceRepository.save(balance);

        return modelMapper.map(balance, Response.class);
    }

    @Override
    public Response update(Long applicationId, UpdateRequest request) {
        return null;
    }

}
