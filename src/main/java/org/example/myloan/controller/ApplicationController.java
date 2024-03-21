package org.example.myloan.controller;

import lombok.RequiredArgsConstructor;
import org.example.myloan.dto.ApplicationDto;
import org.example.myloan.dto.ApplicationDto.AcceptTerms;
import org.example.myloan.dto.ApplicationDto.Request;
import org.example.myloan.dto.ApplicationDto.Response;
import org.example.myloan.dto.ResponseDTO;
import org.example.myloan.service.ApplicationService;
import org.example.myloan.service.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RequestMapping("/applications")
@RestController
public class ApplicationController extends AbstractController{
    private final ApplicationService applicationService;
    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(applicationService.create(request));
    }

    @GetMapping("/{applicationId}")
    public ResponseDTO<Response> get(@PathVariable Long applicationId) {
        return ok(applicationService.get(applicationId));
    }

    @PutMapping("/{applicationId}")
    public ResponseDTO<Response> update(@PathVariable Long applicationId, @RequestBody Request request) {
        return ok(applicationService.update(applicationId, request));
    }

    @DeleteMapping("/{applicationId}")
    public ResponseDTO<Response> delete(@PathVariable Long applicationId) {
        applicationService.delete(applicationId);
        return ok();
    }

    @PostMapping("/{applicationId}/terms")
    public ResponseDTO<Boolean> acceptTerms(@PathVariable Long applicationId, @RequestBody AcceptTerms request) {
        return ok(applicationService.acceptTerms(applicationId, request));
    }

    @PostMapping("/{applicationId}/files")
    public ResponseDTO<Void> upload(@PathVariable Long applicationId, MultipartFile file) {
        fileStorageService.save(applicationId, file);
        return ok();
    }

 }
