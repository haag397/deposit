package com.arch.deposit.aggregate;

import com.arch.deposit.command.deposit.CreateDepositCommand;
import com.arch.deposit.command.deposit.DepositOpenRequestedEvent;
import com.arch.deposit.command.deposit.MarkDepositRequestedCommand;
import com.arch.deposit.event.deposit.DepositCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Objects;

@Aggregate
@NoArgsConstructor
@Slf4j
public class DepositAggregate {

    @AggregateIdentifier
    private String depositId;

    @CommandHandler
    public DepositAggregate(MarkDepositRequestedCommand markDepositRequestedCommand) {
        if (markDepositRequestedCommand.getDepositId() == null || markDepositRequestedCommand.getDepositId().isBlank()) throw new IllegalArgumentException("depositId required");
        if (markDepositRequestedCommand.getUserId() == null || markDepositRequestedCommand.getUserId().isBlank()) throw new IllegalArgumentException("userId required");
        AggregateLifecycle.apply(new DepositOpenRequestedEvent(
                markDepositRequestedCommand.getDepositId(), markDepositRequestedCommand.getUserId(), markDepositRequestedCommand.getDeposType(), markDepositRequestedCommand.getCustomerNumber()
        ));
    }

    @EventSourcingHandler
    public void on(DepositOpenRequestedEvent depositOpenRequestedEvent) {
        this.depositId = depositOpenRequestedEvent.getDepositId();
    }

    @CommandHandler
    public void handle(CreateDepositCommand createDepositCommand) {
        if (!Objects.equals(this.depositId, createDepositCommand.depositId())) {
            throw new IllegalStateException("Wrong aggregate id");
        }
        AggregateLifecycle.apply(new DepositCreatedEvent(
                createDepositCommand.depositId(),
                createDepositCommand.userId(),
                createDepositCommand.statusCode(),
                createDepositCommand.statusMessage(),
                createDepositCommand.transactionDate(),
                createDepositCommand.depositNumber(),
                createDepositCommand.currentAmount(),
                createDepositCommand.currentWithdrawableAmount(),
                createDepositCommand.transactionId(),
                createDepositCommand.deposType(),
                createDepositCommand.customerNumber()       ));
    }

    @EventSourcingHandler
    public void on(DepositCreatedEvent depositCreatedEvent) {
        log.info("ES DepositCreatedEvent depositId={}", depositCreatedEvent.depositId());
        this.depositId = depositCreatedEvent.depositId();
    }
}
