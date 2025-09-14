package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AmountReq(
        @NotBlank String userId,
        @NotBlank String depositId,
        @NotNull @Positive BigDecimal amount
) {}