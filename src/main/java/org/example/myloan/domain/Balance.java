package org.example.myloan.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@SQLRestriction("is_deleted=false")
public class Balance extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long balanceId;

    @Column(columnDefinition = "bigint NOT NULL COMMENT 'Application ID'")
    private Long applicationId;

    @Column(columnDefinition = "decimal(15,2) NOT NULL COMMENT 'Balance'")
    private BigDecimal balance;

}
