package com.arch.deposit.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

/** Command to create a deposit aggregate when the user starts the flow. */
public record StartDepositOpeningCommand(
        @TargetAggregateIdentifier String depositId,
        String userId
) {}

