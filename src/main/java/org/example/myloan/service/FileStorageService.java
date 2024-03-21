package org.example.myloan.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void save(Long applicationId, MultipartFile file);
    Resource load(Long applicationId, String fileName);

}
