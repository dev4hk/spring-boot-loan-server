package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.AcceptTerms;
import org.example.myloan.domain.Application;
import org.example.myloan.domain.Terms;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.AcceptTermsRepository;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.TermsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ApplicationServiceImpl implements ApplicationService{
    private final ApplicationRepository applicationRepository;
    private final TermsRepository termsRepository;
    private final AcceptTermsRepository acceptTermsRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());
        Application created = applicationRepository.save(application);
        return modelMapper.map(created, Response.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Response get(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        return modelMapper.map(application, Response.class);
    }

    @Override
    public Response update(Long applicationId, Request request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        application.setName(request.getName());
        application.setCellPhone(request.getCellPhone());
        application.setEmail(request.getEmail());
        application.setHopeAmount(request.getHopeAmount());

        return modelMapper.map(application, Response.class);
    }

    @Override
    public void delete(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        application.setIsDeleted(true);
    }

    @Override
    public Boolean acceptTerms(Long applicationId, ApplicationDto.AcceptTerms request) {
        applicationRepository.findById(applicationId).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR));
        List<Terms> termsList = termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"));
        if (termsList.isEmpty()){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        List<Long> acceptTermsIds = request.getAcceptTermsIds();
        if(termsList.size() != acceptTermsIds.size()){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        List<Long> termsIds = termsList.stream().map(Terms::getTermsId).collect(Collectors.toList());
        Collections.sort(termsIds);
        if(!termsIds.containsAll(acceptTermsIds)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        for(Long termsId : acceptTermsIds) {
            AcceptTerms accepted = AcceptTerms.builder()
                    .termsId(termsId)
                    .applicationId(applicationId)
                    .build();
            acceptTermsRepository.save(accepted);
        }
        return true;
    }

}
