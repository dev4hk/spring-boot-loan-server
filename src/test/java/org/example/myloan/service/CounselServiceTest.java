package org.example.myloan.service;

import org.example.myloan.domain.Counsel;
import org.example.myloan.dto.CounselDto;
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

import static org.assertj.core.api.Assertions.assertThat;
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
}