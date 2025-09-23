package com.arch.deposit.command.deposit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositOpenRequestedEvent {
    private String depositId;
    private String userId;
    private String deposType;
    private String customerNumber;
}
