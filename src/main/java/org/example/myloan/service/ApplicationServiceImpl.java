package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Application;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.ApplicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ApplicationServiceImpl implements ApplicationService{
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Application application = modelMapper.map(request, Application.class);
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
}
