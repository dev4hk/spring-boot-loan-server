package org.example.myloan.controller;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.CounselDto;
import org.example.myloan.dto.CounselDto.Request;
import org.example.myloan.dto.CounselDto.Response;
import org.example.myloan.dto.ResponseDTO;
import org.example.myloan.service.CounselService;
import org.springframework.web.bind.annotation.*;

import static org.example.myloan.dto.ResponseDTO.ok;

@RequiredArgsConstructor
@RequestMapping("/counsels")
@RestController
public class CounselController {

    private final CounselService counselService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(counselService.create(request));
    }

    @GetMapping("/{counselId}")
    public ResponseDTO<Response> get(@PathVariable Long counselId) {
        return ok(counselService.get(counselId));
    }

    @PutMapping("/{counselId}")
    public ResponseDTO<Response> update(@PathVariable Long counselId, @RequestBody Request request) {
        return ok(counselService.update(counselId, request));
    }
}
