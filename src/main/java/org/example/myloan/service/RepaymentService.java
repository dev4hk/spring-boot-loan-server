package org.example.myloan.service;

import org.example.myloan.dto.RepaymentDto;
import org.example.myloan.dto.RepaymentDto.ListResponse;
import org.example.myloan.dto.RepaymentDto.Request;
import org.example.myloan.dto.RepaymentDto.Response;
import org.example.myloan.dto.RepaymentDto.UpdateResponse;

import java.util.List;

public interface RepaymentService {
    Response create(Long applicationId, Request request);
    List<ListResponse> get(Long applicationId);
    UpdateResponse update(Long repaymentId, Request request);
    void delete(Long repaymentId);
}
