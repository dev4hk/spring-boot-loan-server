package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Counsel;
import org.example.myloan.dto.CounselDto.Request;
import org.example.myloan.dto.CounselDto.Response;
import org.example.myloan.repository.CounselRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService{

    private final ModelMapper modelMapper;
    private final CounselRepository counselRepository;

    @Override
    public Response create(Request request) {
        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());
        Counsel created = counselRepository.save(counsel);
        return modelMapper.map(created, Response.class);
    }
}
