package org.example.myloan.service;

import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.GrantAmount;
import org.example.myloan.dto.JudgmentDto.Request;
import org.example.myloan.dto.JudgmentDto.Response;

public interface JudgmentService {
    Response create(Request request);
    Response get(Long judgmentId);
    Response getJudgmentOfApplication(Long applicationId);
    Response update(Long judgmentId, Request request);
    void delete(Long judgmentId);
    GrantAmount grant(Long judgmentId);
}
