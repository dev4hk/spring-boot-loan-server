package org.example.myloan.service;

import org.example.myloan.dto.JudgmentDto.Request;
import org.example.myloan.dto.JudgmentDto.Response;

public interface JudgmentService {
    Response create(Request request);

}