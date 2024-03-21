package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService{
    private final ApplicationRepository applicationRepository;


    @Override
    public ApplicationDto.Response create(ApplicationDto.Request request) {
        return null;
    }

    @Override
    public ApplicationDto.Response get(Long applicationId) {
        return null;
    }

    @Override
    public ApplicationDto.Response update(Long applicationId, ApplicationDto.Request request) {
        return null;
    }

    @Override
    public void delete(Long applicationId) {

    }
}
