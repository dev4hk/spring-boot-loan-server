package org.example.myloan.controller;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
import org.example.myloan.dto.ResponseDTO;
import org.example.myloan.service.ApplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/applications")
@RestController
public class ApplicationController extends AbstractController{
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return null;
    }
 }
