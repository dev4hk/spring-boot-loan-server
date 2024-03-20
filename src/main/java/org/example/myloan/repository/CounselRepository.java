package org.example.myloan.repository;

import org.example.myloan.domain.Counsel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounselRepository extends JpaRepository<Counsel, Long> {
}
