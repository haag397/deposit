package com.arch.deposit.event;

public record DepositCreatedEvent(
        String depositId,
        String userId,
        String depositNumber,
        String iban,
        String currentAmount,
        String currentWithdrawableAmount
) {}