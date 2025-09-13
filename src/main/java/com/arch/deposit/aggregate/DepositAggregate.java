package com.arch.deposit.aggregate;

import com.arch.deposit.command.SelectDepositTypeCommand;
import com.arch.deposit.command.StartDepositOpeningCommand;
import com.arch.deposit.event.DepositOpeningStartedEvent;
import com.arch.deposit.event.DepositTypeSelectedEvent;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/** Aggregate representing the lifecycle of a deposit opening. */
@Aggregate
public class DepositAggregate {

    @AggregateIdentifier
    private String depositId;
    private String userId;
    private UUID depositTypeId;

    protected DepositAggregate() {
        // Axon requires a no-arg constructor
    }

    @CommandHandler
    public DepositAggregate(StartDepositOpeningCommand cmd) {
        AggregateLifecycle.apply(new DepositOpeningStartedEvent(cmd.depositId(), cmd.userId()));
    }

    @EventSourcingHandler
    public void on(DepositOpeningStartedEvent event) {
        this.depositId = event.depositId();
        this.userId = event.userId();
    }

    @CommandHandler
    public void handle(SelectDepositTypeCommand cmd) {
        if (this.depositTypeId != null) {
            throw new IllegalStateException("Deposit type already selected");
        }
        AggregateLifecycle.apply(new DepositTypeSelectedEvent(cmd.depositId(), cmd.depositTypeId()));
    }

    @EventSourcingHandler
    public void on(DepositTypeSelectedEvent event) {
        this.depositTypeId = event.depositTypeId();
    }
}

