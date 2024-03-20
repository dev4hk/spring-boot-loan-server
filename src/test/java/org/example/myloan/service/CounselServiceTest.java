package org.example.myloan.service;

import org.example.myloan.domain.Counsel;
import org.example.myloan.dto.CounselDto;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.CounselRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CounselServiceTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    CounselRepository counselRepository;

    @Spy
    private ModelMapper modelMapper;

    @DisplayName("Create Counsel")
    @Test
    void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() {
        Counsel entity = Counsel.builder()
                .name("Member Kim")
                .cellPhone("123-456-7890")
                .email("mail@abc.de")
                .memo("I hope to get a loan")
                .zipCode("12345")
                .address("Somewhere in New York, New York")
                .addressDetail("What Apartment No. 101, 1st floor No. 101")
                .build();

        CounselDto.Request request = CounselDto.Request.builder()
                .name("Member Kim")
                .cellPhone("123-456-7890")
                .email("mail@abc.de")
                .memo("I hope to get a loan")
                .zipCode("12345")
                .address("Somewhere in New York, New York")
                .addressDetail("What Apartment No. 101, 1st floor No. 101")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

        CounselDto.Response actual = counselService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
    }

    @DisplayName("Get a counsel by id")
    @Test
    void Should_ReturnResponseOfExistCounselEntity_When_RequestExistCounselId() {
        Long counselId = 1L;
        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();
        when(counselRepository.findById(counselId)).thenReturn(Optional.ofNullable(entity));
        CounselDto.Response actual = counselService.get(counselId);
        assertThat(actual.getCounselId()).isSameAs(counselId);
    }

    @DisplayName("Get non-existing counsel by id")
    @Test
    void Should_ThrowException_When_RequestNotExistCounselId() {
        Long counselId = 2L;
        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));
        assertThrows(BaseException.class, () -> counselService.get(findId));
    }

    @DisplayName("Update a counsel")
    @Test
    void Should_ReturnUpdatedResponseOfExistCounselEntity_When_RequestUpdateExistCounselInfo() {
        Long counselId = 1L;
        Counsel entity = Counsel.builder()
                .counselId(1L)
                .name("Member Kim")
                .build();

        CounselDto.Request request = CounselDto.Request.builder()
                .name("Member Lee")
                .build();

        when(counselRepository.findById(findId)).thenReturn(Optional.of(entity));
        when(counselRepository.save(any(Counsel.class))).thenReturn(entity);

        CounselDto.Response actual = counselService.update(findId, request);

        assertThat(actual.getCounselId()).isSameAs(findId);
        assertThat(actual.getName()).isSameAs(request.getName());

    }

    @DisplayName("Update non-existing counsel")
    @Test
    void Should_ReturnUpdatedResponseOfExistCounselEntity_When_RequestUpdateExistCounselInfo() {
        Long counselId = 1L;

        CounselDto.Request request = CounselDto.Request.builder()
                .name("Member Lee")
                .build();

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));
        assertThrows(BaseException.class, () -> counselService.update(counselId, request));

    }
}