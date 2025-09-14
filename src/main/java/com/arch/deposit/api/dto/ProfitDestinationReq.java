package com.arch.deposit.api.dto;


import jakarta.validation.constraints.NotBlank;

public record ProfitDestinationReq(
        @NotBlank String userId,
        @NotBlank String depositId,
        @NotBlank String destinationType,   // e.g. "DEPOSIT" | "CARD"
        @NotBlank String destinationAccount // IBAN / depositNo / card, as you design
) {}
