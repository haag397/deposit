package com.arch.deposit.core;

import java.util.UUID;

/** DTO representing a deposit type returned by the core banking service. */
public record CoreDepositTypeDTO(UUID id, String name, String description) {}
