package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SelectTypeReq(
        @NotBlank String userId,
        @NotBlank String depositId,
        @NotBlank String name
) {}