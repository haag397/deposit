package com.arch.deposit.infrastructure.feign.core.dto;

import java.util.UUID;

public record CoreDepositTypeDTO(
        String name,
        String description) {}