package org.example.myloan.controller;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.ResponseDTO;
import org.example.myloan.dto.TermsDto.Request;
import org.example.myloan.dto.TermsDto.Response;
import org.example.myloan.service.TermsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.myloan.dto.ResponseDTO.ok;

@RequiredArgsConstructor
@RequestMapping("/terms")
@RestController
public class TermsController {

    private final TermsService termsService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return null;
    }
}
