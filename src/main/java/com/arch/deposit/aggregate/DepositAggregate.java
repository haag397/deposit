package com.arch.deposit.aggregate;

import com.arch.deposit.command.CreateDepositCommand;
import com.arch.deposit.event.DepositCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

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
        AggregateLifecycle.apply(new DepositCreatedEvent(
                cmd.depositId(),
                cmd.userId(),
                cmd.depositNumber(),
                cmd.deposType(),
                cmd.iban(),
                cmd.currentAmount(),
                cmd.currentWithdrawableAmount()
        ));
    }

    @EventSourcingHandler
    public void on(DepositCreatedEvent e) {
        log.info("ES DepositCreatedEvent depositId={}", e.depositId());
        this.depositId = e.depositId();
        this.opened = true;
    }
}
