package org.example.myloan.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@SQLRestriction("is_deleted=false")
public class Counsel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long counselId;

    @Column(nullable = false, columnDefinition = "datetime COMMENT 'Applied At'")
    private LocalDateTime appliedAt;

    @Column(nullable = false, columnDefinition = "varchar(30) COMMENT 'Applicant Name'")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(10) COMMENT 'Applicant Phone Number'")
    private String cellPhone;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT 'Applicant Email'")
    private String email;

    @Column(columnDefinition = "text DEFAULT NULL COMMENT 'Counsel Memo'")
    private String memo;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT 'Address'")
    private String address;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT 'Address Detail'")
    private String addressDetail;

    @Column(columnDefinition = "varchar(5) DEFAULT NULL COMMENT 'Zipcode'")
    private String zipCode;
}
