package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FinalConfirmReq(
        @NotBlank String userId,
        @NotBlank String depositId,
        // what to send to core:
        @NotBlank String deposType,
        @NotBlank String currency,
        @NotNull BigDecimal amount,
        String sourceDeposit,          // optional
        String destDeposit,            // optional
        @NotBlank String customerNumber,
        @NotBlank String currentBranchCode
) {}
