package com.arch.deposit.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

/** Command to create a deposit aggregate by selecting a deposit type. */
public record SelectDepositTypeCommand(
        @TargetAggregateIdentifier String depositId,
        String userId,
        UUID depositTypeId
) {}

