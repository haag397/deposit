package com.arch.deposit.event.deposit_type;

import lombok.Value;

import java.util.UUID;

@Value
public class DepositTypeDeletedEvent{
        UUID id;
}