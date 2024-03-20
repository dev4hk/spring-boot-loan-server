package org.example.myloan.service;

import org.example.myloan.dto.CounselDto;

public interface CounselService {
    CounselDto.Response create(CounselDto.Request request);
}
