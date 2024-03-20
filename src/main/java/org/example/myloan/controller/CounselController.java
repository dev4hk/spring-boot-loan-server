package org.example.myloan.controller;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.CounselDto;
import org.example.myloan.dto.CounselDto.Request;
import org.example.myloan.dto.CounselDto.Response;
import org.example.myloan.dto.ResponseDTO;
import org.example.myloan.service.CounselService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/counsels")
@RestController
public class CounselController {

    private final CounselService counselService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return null;
    }
}
