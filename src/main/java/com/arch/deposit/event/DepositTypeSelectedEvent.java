package com.arch.deposit.event;

import java.util.UUID;

/** Event emitted when a user selects a deposit type. */
public record DepositTypeSelectedEvent(
        String depositId,
        String userId,
        UUID depositTypeId
) {}

