package com.arch.deposit.aggregate;

import com.arch.deposit.command.deposit_type.CreateDepositTypeCommand;
import com.arch.deposit.command.deposit_type.DeleteDepositTypeCommand;
import com.arch.deposit.command.deposit_type.UpdateDepositTypeCommand;
import com.arch.deposit.event.deposit_type.DepositTypeCreatedEvent;
import com.arch.deposit.event.deposit_type.DepositTypeDeletedEvent;
import com.arch.deposit.event.deposit_type.DepositTypeUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class DepositTypeAggregate {

    @AggregateIdentifier
    private UUID id;
    private String name;

    @CommandHandler
    public DepositTypeAggregate(CreateDepositTypeCommand cmd) {
        validateName(cmd.getName());
        AggregateLifecycle.apply(new DepositTypeCreatedEvent(
                cmd.getId(), cmd.getName().trim(), cmd.getDescription()
        ));
    }

    @EventSourcingHandler
    public void on(DepositTypeCreatedEvent e) {
        this.id = e.getId();
        this.name = e.getName();
    }

    @CommandHandler
    public void handle(UpdateDepositTypeCommand cmd) {
        validateName(cmd.getName());
        AggregateLifecycle.apply(new DepositTypeUpdatedEvent(
                cmd.getId(), cmd.getName().trim(), cmd.getDescription()
        ));
    }

    @EventSourcingHandler
    public void on(DepositTypeUpdatedEvent e) {
        this.name = e.name();
    }

    @CommandHandler
    public void handle(DeleteDepositTypeCommand cmd) {
        AggregateLifecycle.apply(new DepositTypeDeletedEvent(cmd.getId()));
    }

    @EventSourcingHandler
    public void on(DepositTypeDeletedEvent e) {
        // Optionally mark deleted; for now nothing needed
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
    }
}
