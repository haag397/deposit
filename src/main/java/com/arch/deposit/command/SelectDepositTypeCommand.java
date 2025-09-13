package com.arch.deposit.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

/** Command to choose a deposit type after the process has started. */
public record SelectDepositTypeCommand(
        @TargetAggregateIdentifier String depositId,
        UUID depositTypeId
) {}

