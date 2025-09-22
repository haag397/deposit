package com.arch.deposit.aggregate;

import com.arch.deposit.command.deposit.CreateDepositCommand;
import com.arch.deposit.event.deposit.DepositCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@Slf4j
public class DepositAggregate {

    @AggregateIdentifier
    private String depositId;
    private boolean opened;

    @CommandHandler
    public DepositAggregate(CreateDepositCommand cmd) {
        log.info("CMD CreateDepositCommand depositId={}", cmd.depositId());

        if (cmd.depositId() == null || cmd.depositId().isBlank())
            throw new IllegalArgumentException("depositId is required");
        if (cmd.userId() == null || cmd.userId().isBlank())
            throw new IllegalArgumentException("userId is required");
        AggregateLifecycle.apply(new DepositCreatedEvent(
                cmd.depositId(),
                cmd.userId(),
                cmd.statusCode(),
                cmd.statusMessage(),
                cmd.transactionDate(),
                cmd.depositNumber(),
                cmd.currentAmount(),
                cmd.currentWithdrawableAmount(),
                cmd.transactionId(),
                cmd.deposType(),
                cmd.customerNumber()       ));
    }

    @EventSourcingHandler
    public void on(DepositCreatedEvent e) {
        log.info("ES DepositCreatedEvent depositId={}", e.depositId());
        this.depositId = e.depositId();
        this.opened = true;
    }
}
