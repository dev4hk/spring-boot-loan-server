package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Application;
import org.example.myloan.domain.Judgment;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.GrantAmount;
import org.example.myloan.dto.JudgmentDto;
import org.example.myloan.dto.JudgmentDto.Request;
import org.example.myloan.dto.JudgmentDto.Response;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.JudgmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Transactional
@Service
public class JudgmentServiceImpl implements JudgmentService{

    private final ApplicationRepository applicationRepository;
    private final JudgmentRepository judgmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Long applicationId = request.getApplicationId();
        if(!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        Judgment judgment = modelMapper.map(request, Judgment.class);
        Judgment created = judgmentRepository.save(judgment);
        return modelMapper.map(created, Response.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Response get(Long judgmentId) {
        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        return modelMapper.map(judgment, Response.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Response getJudgmentOfApplication(Long applicationId) {
        if(!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        Judgment judgment = judgmentRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        return modelMapper.map(judgment, Response.class);
    }

    @Override
    public Response update(Long judgmentId, Request request) {
        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        judgment.setName(request.getName());
        judgment.setApprovalAmount(request.getApprovalAmount());
        return modelMapper.map(judgment, Response.class);
    }

    @Override
    public void delete(Long judgmentId) {
        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        judgment.setIsDeleted(true);
    }

    @Override
    public GrantAmount grant(Long judgmentId) {
        Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR));
        Long applicationId = judgment.getApplicationId();
        Application application = applicationRepository.findById(applicationId).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR));
        BigDecimal approvalAmount = judgment.getApprovalAmount();
        application.setApprovalAmount(approvalAmount);
        return modelMapper.map(application, GrantAmount.class);
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }

}
