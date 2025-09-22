package com.arch.deposit.api.dto.deposit_type;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DepositTypeResponseDTO(
        UUID id,
        String name,
        String description
) {}