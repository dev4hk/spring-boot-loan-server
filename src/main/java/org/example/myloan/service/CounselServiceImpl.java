package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Counsel;
import org.example.myloan.dto.CounselDto.Request;
import org.example.myloan.dto.CounselDto.Response;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.CounselRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
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

    @Transactional(readOnly = true)
    @Override
    public Response get(Long counselId) {
        Counsel counsel = counselRepository.findById(counselId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        return modelMapper.map(counsel, Response.class);
    }

    @Override
    public Response update(Long counselId, Request request) {
        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR));
        counsel.setName(request.getName());
        counsel.setCellPhone(request.getCellPhone());
        counsel.setEmail(request.getEmail());
        counsel.setMemo(request.getMemo());
        counsel.setAddress(request.getAddress());
        counsel.setAddressDetail(request.getAddressDetail());
        counsel.setZipCode(request.getZipCode());
        return modelMapper.map(counsel, Response.class);
    }

    @Override
    public void delete(Long counselId) {

    }
}
