package com.arch.deposit.infrastructure.feign.core.dto;

import java.util.UUID;

public record CoreDepositTypeDTO(
        UUID id,
        String name,
        String description) {}