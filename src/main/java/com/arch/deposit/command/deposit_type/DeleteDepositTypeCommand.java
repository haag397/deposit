package com.arch.deposit.command.deposit_type;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDepositTypeCommand{
        @TargetAggregateIdentifier
        private UUID id;
}
