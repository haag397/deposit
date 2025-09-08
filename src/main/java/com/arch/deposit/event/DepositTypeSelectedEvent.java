package com.arch.deposit.event;

import java.util.UUID;

public record DepositTypeSelectedEvent(
        String depositId,
        String userId,
        UUID depositTypeId
) {}