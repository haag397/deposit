package com.arch.deposit.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateDepositCommand(
        @TargetAggregateIdentifier String depositId,
        String userId,
        String depositNumber,
        UUID depositTypeId,
        BigDecimal amount,
        String profitDestinationType,
        String profitDestinationAccount
) {}