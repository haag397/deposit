package com.arch.deposit.api.dto.deposit_type;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DepositTypeUpdateDTO (
        @NotBlank String name,
        String description,
        String code
) {}
