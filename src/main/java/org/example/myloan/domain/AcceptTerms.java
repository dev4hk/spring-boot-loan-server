package org.example.myloan.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@SQLRestriction("is_deleted=false")
public class AcceptTerms extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long acceptTermsId;

    @Column(columnDefinition = "bigint NOT NULL COMMENT 'Application ID'")
    private Long applicationId;

    @Column(columnDefinition = "bigint NOT NULL COMMENT 'Terms ID'")
    private Long termsId;

}
