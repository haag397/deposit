package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

public record FinalConfirmReq(
        @NotBlank String userId,
        @NotBlank String depositId,

        // include what you want to persist
        String depositNumber,
        UUID depositTypeId,
        BigDecimal amount,
        String profitDestinationType,
        String profitDestinationAccount
) {}