package com.arch.deposit.event.deposit_type;

import lombok.Value;

import java.util.UUID;

@Value
public class DepositTypeUpdatedEvent {
    UUID id;
    String name;
    String code;
    String description;
}