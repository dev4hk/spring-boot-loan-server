package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Application;
import org.example.myloan.domain.Entry;
import org.example.myloan.dto.EntryDto;
import org.example.myloan.dto.EntryDto.Request;
import org.example.myloan.dto.EntryDto.Response;
import org.example.myloan.dto.EntryDto.UpdateResponse;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.EntryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
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

    @Transactional(readOnly = true)
    @Override
    public Response get(Long applicationId) {
        Optional<Entry> entry = entryRepository.findByApplicationId(applicationId);
        if(entry.isEmpty()) {
            return null;
        }
        return modelMapper.map(entry, Response.class);
    }

    @Override
    public UpdateResponse update(Long entryId, Request request) {
        Entry entry = entryRepository.findById(entryId).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR));
        BigDecimal beforeEntryAmount = entry.getEntryAmount();
        entry.setEntryAmount(request.getEntryAmount());
        Long applicationId = entry.getApplicationId();
        return EntryDto.UpdateResponse.builder()
                .entryId(entryId)
                .applicationId(applicationId)
                .beforeEntryAmount(beforeEntryAmount)
                .afterEntryAmount(request.getEntryAmount())
                .build();
    }

    @Override
    public void delete(Long entryId) {
        Entry entry = entryRepository.findById(entryId).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR));
        entry.setIsDeleted(true);
    }
}
