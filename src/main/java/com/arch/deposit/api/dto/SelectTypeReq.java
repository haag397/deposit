package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SelectTypeReq(
        @NotBlank String userId,
        @NotBlank String depositId,
        @NotNull UUID typeId
) {}