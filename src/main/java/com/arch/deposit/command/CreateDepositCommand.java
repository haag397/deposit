package com.arch.deposit.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateDepositCommand(
        @TargetAggregateIdentifier String depositId,
        String userId,
        String depositNumber,
        String deposType,
        String iban,
        String currentAmount,                 // keep as String; projector can parse if you want
        String currentWithdrawableAmount
) {}