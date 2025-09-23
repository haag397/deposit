package com.arch.deposit.event.deposit_type;

import lombok.Value;

import java.util.UUID;
@Value
public class DepositTypeCreatedEvent {
    UUID id;
    String name;
    String code;
    String description;
    }