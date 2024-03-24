package org.example.myloan.service;

import org.example.myloan.dto.EntryDto;
import org.example.myloan.dto.EntryDto.Request;
import org.example.myloan.dto.EntryDto.Response;
import org.example.myloan.dto.EntryDto.UpdateResponse;

public interface EntryService {

    Response create(Long applicationId, Request request);
    Response get(Long applicationId);
    UpdateResponse update(Long entryId, Request request);
    void delete(Long entryId);

}
