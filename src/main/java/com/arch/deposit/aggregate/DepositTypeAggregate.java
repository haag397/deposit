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
    public DepositTypeAggregate(CreateDepositTypeCommand createDepositTypeCommand) {
        validateName(createDepositTypeCommand.getName());
        AggregateLifecycle.apply(new DepositTypeCreatedEvent(
                createDepositTypeCommand.getId(),
                createDepositTypeCommand.getName().trim(),
                createDepositTypeCommand.getDescription(),
                createDepositTypeCommand.getCode()
        ));
    }

    @EventSourcingHandler
    public void on(DepositTypeCreatedEvent depositTypeCreatedEvent) {
        this.id = depositTypeCreatedEvent.getId();
        this.name = depositTypeCreatedEvent.getName();
    }

    @CommandHandler
    public void handle(UpdateDepositTypeCommand updateDepositTypeCommand) {
        validateName(updateDepositTypeCommand.getName());
        AggregateLifecycle.apply(new DepositTypeUpdatedEvent(
                updateDepositTypeCommand.getId(),
                updateDepositTypeCommand.getName().trim(),
                updateDepositTypeCommand.getDescription(),
                updateDepositTypeCommand.getCode()
        ));
    }

    @EventSourcingHandler
    public void on(DepositTypeUpdatedEvent depositTypeUpdatedEvent) {
        this.name = depositTypeUpdatedEvent.getName();
    }

    @CommandHandler
    public void handle(DeleteDepositTypeCommand deleteDepositTypeCommand) {
        AggregateLifecycle.apply(new DepositTypeDeletedEvent(deleteDepositTypeCommand.getId()));
    }

    @EventSourcingHandler
    public void on(DepositTypeDeletedEvent depositTypeDeletedEvent) {
        // Optionally mark deleted; for now nothing needed
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
    }
}
