package com.arch.deposit.aggregate;

import com.arch.deposit.command.SelectDepositTypeCommand;
import com.arch.deposit.command.StartDepositOpeningCommand;
import com.arch.deposit.event.DepositOpeningStartedEvent;
import com.arch.deposit.event.DepositTypeSelectedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class DepositAggregate {
    @AggregateIdentifier
    private String depositId;
    private String userId;
    private UUID depositTypeId;
    private boolean started;

    protected DepositAggregate() {
    }

    @CommandHandler
    public DepositAggregate(SelectDepositTypeCommand cmd) {
        AggregateLifecycle.apply(new DepositTypeSelectedEvent(
                cmd.depositId(), cmd.userId(), cmd.depositTypeId()));
    }

    @EventSourcingHandler
    public void on(DepositTypeSelectedEvent event) {
        this.depositId = event.depositId();
        this.userId = event.userId();
        this.depositTypeId = event.depositTypeId();
        this.started = false;
    }

    @CommandHandler
    public void handle(StartDepositOpeningCommand cmd) {
        if (started) {
            throw new IllegalStateException("Deposit already started");
        }
        AggregateLifecycle.apply(new DepositOpeningStartedEvent(cmd.depositId()));
    }

    @EventSourcingHandler
    public void on(DepositOpeningStartedEvent event) {
        this.started = true;
    }
}
