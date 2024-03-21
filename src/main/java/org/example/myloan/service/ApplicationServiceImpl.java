package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Application;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
import org.example.myloan.repository.ApplicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
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

    @Override
    public Response get(Long applicationId) {
        return null;
    }

    @Override
    public Response update(Long applicationId, Request request) {
        return null;
    }

    @Override
    public void delete(Long applicationId) {

    }
}
