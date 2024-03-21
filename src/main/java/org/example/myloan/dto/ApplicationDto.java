package org.example.myloan.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ApplicationDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Request {
        private String name;
        private String cellPhone;
        private String email;
        private BigDecimal hopeAmount;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {
        private Long applicationId;
        private String name;
        private String cellPhone;
        private String email;
        private BigDecimal hopeAmount;
        private LocalDateTime appliedAt;
        private LocalDateTime createdAt;
        private LocalDateTime contractedAt;
        private LocalDateTime updatedAt;
    }
}