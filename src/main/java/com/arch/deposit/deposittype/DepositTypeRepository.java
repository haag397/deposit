package com.arch.deposit.deposittype;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositTypeRepository extends JpaRepository<DepositType, UUID> {
}
