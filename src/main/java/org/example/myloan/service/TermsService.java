package org.example.myloan.service;

import org.example.myloan.dto.TermsDto;
import org.example.myloan.dto.TermsDto.Request;
import org.example.myloan.dto.TermsDto.Response;

import java.util.List;

public interface TermsService {
    Response create(Request request);
    List<Response> getAll();
}
