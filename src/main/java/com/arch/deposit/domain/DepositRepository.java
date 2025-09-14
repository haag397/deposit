package com.arch.deposit.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, UUID> {
    boolean existsByDepositNumber(String depositNumber);

    // or, if you want uniqueness by (mainCustomerNumber, depositNumber):
    boolean existsByMainCustomerNumberAndDepositNumber(String mainCustomerNumber, String depositNumber);
}