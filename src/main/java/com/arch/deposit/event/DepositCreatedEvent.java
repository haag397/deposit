package com.arch.deposit.event;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositCreatedEvent(
        String depositId,
        String userId,
        String depositNumber,
        UUID depositTypeId,
        BigDecimal amount,
        String profitDestinationType,
        String profitDestinationAccount
) {}