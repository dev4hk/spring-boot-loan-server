package org.example.myloan.repository;

import org.example.myloan.domain.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByApplicationId(Long applicationId);
}
