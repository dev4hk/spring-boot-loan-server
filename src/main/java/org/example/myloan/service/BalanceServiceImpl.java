package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.BalanceDto;
import org.example.myloan.dto.BalanceDto.Request;
import org.example.myloan.dto.BalanceDto.Response;
import org.example.myloan.dto.BalanceDto.UpdateRequest;
import org.example.myloan.repository.BalanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        return null;
    }

    @Override
    public Response update(Long applicationId, UpdateRequest request) {
        return null;
    }

}
