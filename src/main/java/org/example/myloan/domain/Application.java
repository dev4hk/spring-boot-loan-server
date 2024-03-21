package org.example.myloan.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
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
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column(columnDefinition = "varchar(30) DEFAULT NULL COMMENT 'Applicant'")
    private String name;

    @Column(columnDefinition = "varchar(12) DEFAULT NULL COMMENT 'Phone Number'")
    private String cellPhone;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT 'Applicant Email'")
    private String email;

    @Column(columnDefinition = "decimal(5,4) DEFAULT NULL COMMENT 'Interest Rate'")
    private BigDecimal interestRate;

    @Column(columnDefinition = "decimal(5,4) DEFAULT NULL COMMENT 'Application Fee'")
    private BigDecimal fee;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT 'Maturity'")
    private LocalDateTime maturity;

    @Column(columnDefinition = "decimal(15,2) DEFAULT NULL COMMENT 'Hope Amount'")
    private BigDecimal hopeAmount;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT 'Applied At'")
    private LocalDateTime appliedAt;

    @Column(columnDefinition = "decimal(15,2) DEFAULT NULL COMMENT 'Approved Amount'")
    private BigDecimal approvalAmount;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT 'Contracted At'")
    private LocalDateTime contractedAt;
}
