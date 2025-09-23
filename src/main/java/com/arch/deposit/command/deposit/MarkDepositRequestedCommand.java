package com.arch.deposit.command.deposit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkDepositRequestedCommand {
    @TargetAggregateIdentifier
    private String depositId;
    private String userId;
    private String deposType;       // optional at this poin
    private String customerNumber;
}
