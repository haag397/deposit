package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContinueReq(
        @NotBlank String userId,
        @NotBlank String depositId,
        @NotNull Boolean message
) {}