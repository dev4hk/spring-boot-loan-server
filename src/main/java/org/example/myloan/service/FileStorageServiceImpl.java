package org.example.myloan.service;

import lombok.RequiredArgsConstructor;
import org.example.myloan.exception.BaseException;
import org.example.myloan.exception.ResultType;
import org.example.myloan.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;
    private final ApplicationRepository applicationRepository;

    @Override
    public void save(Long applicationId, MultipartFile file) {
        if(!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        try {
            String applicationPath = uploadPath.concat("/" + applicationId);
            Path directory = Path.of(applicationPath);
            if(!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.copy(
                    file.getInputStream(),
                    Paths.get(applicationPath).resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public Resource load(Long applicationId, String fileName) {
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        try {
            String applicationPath = uploadPath.concat("/" + applicationId);
            Path file = Paths.get(applicationPath).resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.isReadable() || resource.exists()) {
                return resource;
            } else {
                throw new BaseException(ResultType.NOT_EXIST);
            }

        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }
}
