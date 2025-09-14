package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AcceptRulesReq(
        @NotBlank String userId,
        @NotBlank String depositId,
        @NotNull Boolean accepted
) {}