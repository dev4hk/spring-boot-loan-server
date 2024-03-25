package org.example.myloan.service;

import org.example.myloan.dto.BalanceDto;
import org.example.myloan.dto.BalanceDto.RepaymentRequest;
import org.example.myloan.dto.BalanceDto.Request;
import org.example.myloan.dto.BalanceDto.Response;
import org.example.myloan.dto.BalanceDto.UpdateRequest;

public interface BalanceService {

    Response create(Long applicationId, Request request);
    Response get(Long applicationId);
    Response update(Long applicationId, UpdateRequest request);
    Response repaymentUpdate(Long applicationId, RepaymentRequest request);
    void delete(Long applicationId);

}
