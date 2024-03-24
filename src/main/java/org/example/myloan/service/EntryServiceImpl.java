package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.EntryDto;
import org.example.myloan.dto.EntryDto.Request;
import org.example.myloan.dto.EntryDto.Response;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.EntryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService{

    private final EntryRepository entryRepository;

    private final ApplicationRepository applicationRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        return null;
    }

    @Override
    public Response get(Long applicationId) {
        return null;
    }

    @Override
    public EntryDto.UpdateResponse update(Long entryId, Request request) {
        return null;
    }

    @Override
    public void delete(Long entryId) {

    }
}
