package com.arch.deposit.projection;

import com.arch.deposit.domain.Deposit;
import com.arch.deposit.domain.DepositRepository;
import com.arch.deposit.event.DepositCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DepositProjector {

    private final DepositRepository repo;

    @EventHandler
    @Transactional
    public void on(DepositCreatedEvent e) {

        // if you switched to existsByDepositNumber(...)
        if (repo.existsByDepositNumber(e.depositNumber())) {
            return; // already projected
        }

        var d = new Deposit();
        d.setId(UUID.fromString(e.depositId()));
        // d.setUserId(...)  // <-- removed on purpose
        d.setDepositNumber(e.depositNumber());
        // ... map the rest
        repo.save(d);
    }
}