package org.example.myloan.controller;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.ResponseDTO;
import org.example.myloan.dto.TermsDto.Request;
import org.example.myloan.dto.TermsDto.Response;
import org.example.myloan.service.TermsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.myloan.dto.ResponseDTO.ok;

@RequiredArgsConstructor
@RequestMapping("/terms")
@RestController
public class TermsController {

    private final TermsService termsService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(termsService.create(request));
    }

    @GetMapping
    public ResponseDTO<List<Response>> getAll() {
        return ok(termsService.getAll());
    }
}
