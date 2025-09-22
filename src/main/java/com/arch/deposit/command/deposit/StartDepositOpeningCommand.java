package com.arch.deposit.command.deposit;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

/** Command to signal that the user started the deposit opening. */
public record StartDepositOpeningCommand(
        @TargetAggregateIdentifier
        String depositId
) {}