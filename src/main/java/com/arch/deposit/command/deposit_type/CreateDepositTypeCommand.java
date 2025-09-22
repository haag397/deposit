package com.arch.deposit.command.deposit_type;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepositTypeCommand {
    @TargetAggregateIdentifier
    private UUID id;
    private String name;
    private String description;
}