package com.arch.deposit.projection;

import com.arch.deposit.domain.*;
import com.arch.deposit.event.deposit.DepositCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ProcessingGroup("deposit-projections")
@RequiredArgsConstructor
public class DepositProjection {
    private final DepositOpenRequestRepository repo;

    @EventHandler
    @Transactional
    public void on(DepositCreatedEvent e) {
        // If you want idempotency based on depositNumber you can keep this, otherwise remove.
        if (e.depositNumber() != null && repo.existsByDepositNumber(e.depositNumber())) return;

        DepositOpeningStatus appStatus = switch (e.statusCode() != null ? e.statusCode() : -1) {
            case 200 -> DepositOpeningStatus.SUCCESS;
            case 400 -> DepositOpeningStatus.FAILED;
            case 422 -> DepositOpeningStatus.ERROR;
            default  -> DepositOpeningStatus.FAILED;
        };

        var d = new DepositOpenRequest();
        d.setStatus(appStatus);
        d.setStatusMessage(e.statusMessage());
        d.setTransactionDate(e.transactionDate());
        d.setDepositNumber(e.depositNumber());
        d.setCurrentAmount(e.currentAmount());
        d.setCurrentWithdrawableAmount(e.currentWithdrawableAmount());
        d.setTransactionId(e.transactionId());

        // <<< save these ONLY when SUCCESS >>>
        if (appStatus == DepositOpeningStatus.SUCCESS) {
            d.setDeposType(e.deposType());
            d.setCustomerNumber(e.customerNumber());
        }

        repo.save(d);
    }
}