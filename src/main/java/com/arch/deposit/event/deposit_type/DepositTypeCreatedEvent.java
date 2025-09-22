package com.arch.deposit.event.deposit_type;

import lombok.Value;

import java.util.UUID;
@Value
public class DepositTypeCreatedEvent {
    private UUID id;
    private String name;
    private String description;
    }