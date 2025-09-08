package com.arch.deposit.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

/** Command to create a deposit aggregate by selecting a deposit type. */
public record SelectDepositTypeCommand(
        @TargetAggregateIdentifier
        String depositId,
        String userId,
        UUID depositTypeId
) {}