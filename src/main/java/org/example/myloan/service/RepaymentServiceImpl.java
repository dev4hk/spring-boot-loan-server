package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.domain.Application;
import org.example.myloan.domain.Entry;
import org.example.myloan.domain.Repayment;
import org.example.myloan.dto.BalanceDto;
import org.example.myloan.dto.BalanceDto.RepaymentRequest;
import org.example.myloan.dto.BalanceDto.Response;
import org.example.myloan.dto.RepaymentDto;
import org.example.myloan.dto.RepaymentDto.ListResponse;
import org.example.myloan.dto.RepaymentDto.UpdateResponse;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.ApplicationRepository;
import org.example.myloan.repository.EntryRepository;
import org.example.myloan.repository.RepaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RepaymentServiceImpl implements RepaymentService {

    private final ApplicationRepository applicationRepository;
    private final BalanceService balanceService;
    private final EntryRepository entryRepository;
    private final RepaymentRepository repaymentRepository;

    private final ModelMapper modelMapper;

    @Override
    public RepaymentDto.Response create(Long applicationId, RepaymentDto.Request request) {

        if (!isRepayableApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Repayment repayment = modelMapper.map(request, Repayment.class);
        repayment.setApplicationId(applicationId);

        repaymentRepository.save(repayment);

        Response updatedBalance = balanceService.repaymentUpdate(applicationId,
                RepaymentRequest.builder()
                        .repaymentAmount(request.getRepaymentAmount())
                        .type(RepaymentRequest.RepaymentType.REMOVE)
                        .build());

        RepaymentDto.Response response = modelMapper.map(repayment, RepaymentDto.Response.class);
        response.setBalance(updatedBalance.getBalance());

        return response;
    }

    @Override
    public List<ListResponse> get(Long applicationId) {
        List<Repayment> repayments = repaymentRepository.findAllByApplicationId(applicationId);
        return repayments.stream().map(r -> modelMapper.map(r, ListResponse.class)).collect(Collectors.toList());
    }

    @Override
    public UpdateResponse update(Long repaymentId, RepaymentDto.Request request) {
        Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        Long applicationId = repayment.getApplicationId();
        BigDecimal beforeRepaymentAmount = repayment.getRepaymentAmount();

        balanceService.repaymentUpdate(applicationId,
                RepaymentRequest.builder()
                        .repaymentAmount(beforeRepaymentAmount)
                        .type(RepaymentRequest.RepaymentType.ADD)
                        .build()
        );

        repayment.setRepaymentAmount(request.getRepaymentAmount());
        repaymentRepository.save(repayment);

        Response updatedBalance = balanceService.repaymentUpdate(applicationId,
                RepaymentRequest.builder()
                        .repaymentAmount(request.getRepaymentAmount())
                        .type(RepaymentRequest.RepaymentType.REMOVE)
                        .build()
        );

        return UpdateResponse.builder()
                .applicationId(applicationId)
                .beforeRepaymentAmount(beforeRepaymentAmount)
                .afterRepaymentAmount(request.getRepaymentAmount())
                .balance(updatedBalance.getBalance())
                .createdAt(repayment.getCreatedAt())
                .updatedAt(repayment.getUpdatedAt())
                .build();
    }

    @Override
    public void delete(Long repaymentId) {
        Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        Long applicationId = repayment.getApplicationId();
        BigDecimal removeRepaymentAmount = repayment.getRepaymentAmount();

        balanceService.repaymentUpdate(
                applicationId,
                RepaymentRequest.builder()
                        .repaymentAmount(removeRepaymentAmount)
                        .type(RepaymentRequest.RepaymentType.ADD)
                        .build());

        repayment.setIsDeleted(true);
        repaymentRepository.save(repayment);
    }

    private boolean isRepayableApplication(Long applicationId) {
        Optional<Application> existedApplication = applicationRepository.findById(applicationId);
        if (existedApplication.isEmpty()) {
            return false;
        }

        if (existedApplication.get().getContractedAt() == null) {
            return false;
        }

        Optional<Entry> existedEntry = entryRepository.findByApplicationId(applicationId);
        return existedEntry.isPresent();

    }
}
