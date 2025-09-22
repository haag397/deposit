package com.arch.deposit.command.deposit;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateDepositCommand(
        @TargetAggregateIdentifier
        String depositId,
        String userId,
        Integer statusCode,
        String statusMessage,

        String transactionDate,
        String depositNumber,
        String currentAmount,
        String currentWithdrawableAmount,
        String transactionId,
        String deposType,
        String customerNumber) {}