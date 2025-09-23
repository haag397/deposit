package com.arch.deposit.projection;

import com.arch.deposit.command.deposit.DepositOpenRequestedEvent;
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
    private final DepositOpenRequestRepository depositOpenRequestRepository;

    @EventHandler
    @Transactional
    public void on(DepositOpenRequestedEvent depositOpenRequestedEvent) {
        // create row if not exists; otherwise ignore
        depositOpenRequestRepository.findByCorrelationId(depositOpenRequestedEvent.getDepositId()).orElseGet(() -> {
            var d = DepositOpenRequest.builder()
                    .correlationId(depositOpenRequestedEvent.getDepositId())
                    .status(DepositOpeningStatus.REQUESTED)
                    .deposType(depositOpenRequestedEvent.getDeposType())
                    .customerNumber(depositOpenRequestedEvent.getCustomerNumber())
                    .build();
            return depositOpenRequestRepository.save(d);
        });
    }

    @EventHandler
    @Transactional
    public void on(DepositCreatedEvent depositCreatedEvent) {
        // UPSERT by correlationId
        var row = depositOpenRequestRepository.findByCorrelationId(depositCreatedEvent.depositId())
                .orElseGet(() -> {
                    var d = new DepositOpenRequest();
                    d.setCorrelationId(depositCreatedEvent.depositId());
                    d.setStatus(DepositOpeningStatus.REQUESTED); // starting point if requested was skipped
                    return d;
                });

        // map status
        DepositOpeningStatus appStatus = switch (depositCreatedEvent.statusCode() != null ? depositCreatedEvent.statusCode() : -1) {
            case 200 -> DepositOpeningStatus.SUCCESS;
            case 400 -> DepositOpeningStatus.ERROR;
            case 422 -> DepositOpeningStatus.ERROR;
            default  -> DepositOpeningStatus.UNKNOWN;
        };

        row.setStatus(appStatus);
        row.setStatusMessage(depositCreatedEvent.statusMessage());
        row.setTransactionDate(depositCreatedEvent.transactionDate());
        row.setDepositNumber(depositCreatedEvent.depositNumber());
        row.setCurrentAmount(depositCreatedEvent.currentAmount());
        row.setCurrentWithdrawableAmount(depositCreatedEvent.currentWithdrawableAmount());
        row.setTransactionId(depositCreatedEvent.transactionId());

        // keep request values for audit (you asked to save on success; keeping them regardless is often helpful)
        if (depositCreatedEvent.deposType() != null) row.setDeposType(depositCreatedEvent.deposType());
        if (depositCreatedEvent.customerNumber() != null) row.setCustomerNumber(depositCreatedEvent.customerNumber());

        // If you want to keep "save ONLY on success" for deposType/customerNumber, then:
        // if (appStatus == DepositOpeningStatus.SUCCESS) { row.setDeposType(e.deposType()); row.setCustomerNumber(e.customerNumber()); }

        depositOpenRequestRepository.save(row);
    }
}