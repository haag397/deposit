package com.arch.deposit.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
public record DepositTypeResponse (
        UUID id,
        String name,
        String description
) {}