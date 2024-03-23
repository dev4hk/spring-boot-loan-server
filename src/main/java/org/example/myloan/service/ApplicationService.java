package org.example.myloan.service;

import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.AcceptTerms;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;

public interface ApplicationService {
    Response create(Request request);
    Response get(Long applicationId);
    Response update(Long applicationId, Request request);
    void delete(Long applicationId);
    Boolean acceptTerms(Long applicationId, AcceptTerms request);
    Response contract(Long applicationId);
}
