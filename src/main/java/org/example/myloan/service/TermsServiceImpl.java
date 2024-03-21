package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.TermsDto;
import org.example.myloan.repository.TermsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class TermsServiceImpl implements TermsService{

    private final TermsRepository termsRepository;

    @Override
    public TermsDto.Response create(TermsDto.Request request) {
        return null;
    }

    @Override
    public List<TermsDto.Response> getAll() {
        return null;
    }
}
