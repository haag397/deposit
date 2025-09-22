package com.arch.deposit.event.deposit;

public record DepositCreatedEvent(
        String depositId,
        String userId,

        Integer statusCode,
        String  statusMessage,

        String transactionDate,
        String depositNumber,
        String currentAmount,
        String currentWithdrawableAmount,
        String transactionId,

        String deposType,
        String customerNumber
) {}