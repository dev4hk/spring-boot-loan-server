package org.example.myloan.service;

import org.example.myloan.dto.CounselDto;
import org.example.myloan.dto.CounselDto.Request;
import org.example.myloan.dto.CounselDto.Response;

public interface CounselService {
    Response create(Request request);
    Response get(Long counselId);
    Response update(Long counselId, Request request);
    void delete(Long counselId);
}
