package com.arch.deposit.event.deposit_type;

import java.util.UUID;

public record DepositTypeDeletedEvent(
        UUID id
) {}