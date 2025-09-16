package com.arch.deposit.aggregate;

import com.arch.deposit.command.CreateDepositCommand;
import com.arch.deposit.event.DepositCreatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class DepositAggregate {

    @AggregateIdentifier
    private String depositId;
    private boolean opened;

    @CommandHandler
    public DepositAggregate(CreateDepositCommand cmd) {
        AggregateLifecycle.apply(new DepositCreatedEvent(
                cmd.depositId(),
                cmd.userId(),
                cmd.depositNumber(),
                cmd.iban(),
                cmd.currentAmount(),
                cmd.currentWithdrawableAmount()
        ));
    }

    @EventSourcingHandler
    public void on(DepositCreatedEvent e) {
        this.depositId = e.depositId();
        this.opened = true;
    }
}
