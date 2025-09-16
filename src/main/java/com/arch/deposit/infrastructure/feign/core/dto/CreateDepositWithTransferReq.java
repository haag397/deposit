package com.arch.deposit.infrastructure.feign.core.dto;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;

public record CreateDepositWithTransferReq(
        String depositType,
        String currency,
        String amount,
        @Nullable String sourceDeposit,   // optional
        @Nullable String destDeposit,     // optional
        String customerNumber,
        String currentBranchCode
) {}