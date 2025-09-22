package com.arch.deposit.event.deposit_type;

import java.util.UUID;

public record DepositTypeUpdatedEvent(
        UUID id,
        String name,
        String description
) {}