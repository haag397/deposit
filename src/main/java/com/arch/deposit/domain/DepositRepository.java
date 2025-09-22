package com.arch.deposit.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface DepositRepository extends JpaRepository<Deposit, UUID> {
    boolean existsByDepositNumber(String depositNumber);

    // or, if you want uniqueness by (mainCustomerNumber, depositNumber):
    boolean existsByMainCustomerNumberAndDepositNumber(String mainCustomerNumber, String depositNumber);
}