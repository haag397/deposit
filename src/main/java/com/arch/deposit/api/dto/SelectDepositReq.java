package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;

public record SelectDepositReq(
        @NotBlank String userId,
        @NotBlank String depositId,
        @NotBlank String productCode,
        String iban,
        String cardPan
) {}
