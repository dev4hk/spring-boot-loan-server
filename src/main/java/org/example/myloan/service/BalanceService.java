package org.example.myloan.service;

import org.example.myloan.dto.BalanceDto;
import org.example.myloan.dto.BalanceDto.Request;
import org.example.myloan.dto.BalanceDto.Response;
import org.example.myloan.dto.BalanceDto.UpdateRequest;

public interface BalanceService {

    Response create(Long applicationId, Request request);
    Response update(Long applicationId, UpdateRequest request);

}
