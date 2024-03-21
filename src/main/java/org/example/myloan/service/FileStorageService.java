package org.example.myloan.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void save(Long applicationId, MultipartFile file);

}
