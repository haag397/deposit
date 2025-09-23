package com.arch.deposit.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository

public interface DepositOpenRequestRepository extends JpaRepository<DepositOpenRequest, UUID> {
    Optional<DepositOpenRequest> findByCorrelationId(String correlationId);
    boolean existsByCorrelationId(String correlationId);
    boolean existsByDepositNumber(String depositNumber);}
