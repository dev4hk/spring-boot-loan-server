package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Application;
import org.example.myloan.domain.Entry;
import org.example.myloan.dto.EntryDto;
import org.example.myloan.dto.EntryDto.Request;
import org.example.myloan.dto.EntryDto.Response;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.EntryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService{

    private final EntryRepository entryRepository;

    private final ApplicationRepository applicationRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        if(!isContractedApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        Entry entry = modelMapper.map(request, Entry.class);
        entry.setApplicationId(applicationId);
        entryRepository.save(entry);
        return modelMapper.map(entry, Response.class);
    }

    private boolean isContractedApplication(Long applicationId) {
        Optional<Application> application = applicationRepository.findById(applicationId);
        if(application.isEmpty()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        return application.get().getContractedAt() != null;
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
