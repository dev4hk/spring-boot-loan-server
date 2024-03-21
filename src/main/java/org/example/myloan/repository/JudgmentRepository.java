package org.example.myloan.repository;

import org.example.myloan.domain.Judgment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JudgmentRepository extends JpaRepository<Judgment, Long> {
}
