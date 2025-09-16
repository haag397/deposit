package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;

    public record StartSessionReq(
            @NotBlank String userId,
            @NotBlank String customerNumber
    ) {}