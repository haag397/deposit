package com.arch.deposit.event;

/** Event emitted when the deposit opening is started. */
public record DepositOpeningStartedEvent(
        String depositId,
        String userId
) {}

