package com.arch.deposit.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, String> {
    List<Deposit> findByMainCustomerNumber(String mainCustomerNumber);
    void deleteByMainCustomerNumber(String mainCustomerNumber);
}

