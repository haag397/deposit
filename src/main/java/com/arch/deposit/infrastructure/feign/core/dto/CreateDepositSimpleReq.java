package com.arch.deposit.infrastructure.feign.core.dto;

import java.math.BigDecimal;

public record CreateDepositSimpleReq(
        String depositType,
        String currency,
//        BigDecimal amount,
        String amount,             // NOTE: Core expects STRING amounts
        String customerNumber,
        String currentBranchCode
) {}
