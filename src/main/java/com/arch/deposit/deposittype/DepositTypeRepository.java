package com.arch.deposit.deposittype;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/** Repository for available deposit types. */
public interface DepositTypeRepository extends JpaRepository<DepositType, UUID> {

    Optional<DepositType> findByName(String name);
}

