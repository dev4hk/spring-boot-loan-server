package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Terms;
import org.example.myloan.dto.TermsDto;
import org.example.myloan.dto.TermsDto.Request;
import org.example.myloan.dto.TermsDto.Response;
import org.example.myloan.repository.TermsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class TermsServiceImpl implements TermsService{

    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Terms terms = modelMapper.map(request, Terms.class);
        Terms created = termsRepository.save(terms);
        return modelMapper.map(created, Response.class);
    }

    @Override
    public List<Response> getAll() {
        List<Terms> termsList = termsRepository.findAll();
        return termsList.stream().map(terms -> modelMapper.map(terms, Response.class)).collect(Collectors.toList());
    }
}
