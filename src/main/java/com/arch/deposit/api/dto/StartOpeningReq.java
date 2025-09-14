package com.arch.deposit.api.dto;

import jakarta.validation.constraints.NotBlank;

public record StartOpeningReq(
        @NotBlank String userId,
        @NotBlank String depositId
) {}