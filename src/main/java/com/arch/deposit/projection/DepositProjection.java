package com.arch.deposit.projection;

import com.arch.deposit.domain.Deposit;
import com.arch.deposit.domain.DepositRepository;
import com.arch.deposit.event.DepositCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@ProcessingGroup("deposit-projections")
@RequiredArgsConstructor
public class DepositProjection {
    private final DepositRepository repo;

    @EventHandler
    @Transactional
    public void on(DepositCreatedEvent e) {
//        log.info("Handling DepositCreatedEvent for depositId={} number={}", e.depositId(), e.depositNumber());
        // id = process correlation id
        UUID id = UUID.fromString(e.depositId());

        // idempotency: if already there, skip
//        if (repo.findById(id).isPresent()) return;
//        if (repo.existsByDepositNumber(e.depositNumber())) return;

        var d = new Deposit();
        d.setId(id);
        d.setDepositNumber(e.depositNumber());
        d.setDepositIban(e.iban());

        // If you want to store numerics, parse safely:
        if (e.currentAmount() != null) {
            try { d.setActualAmount(new BigDecimal(e.currentAmount())); } catch (Exception ignore) {}
        }
        if (e.currentWithdrawableAmount() != null) {
            try { d.setAvailableAmount(new BigDecimal(e.currentWithdrawableAmount())); } catch (Exception ignore) {}
        }
        repo.save(d);
    }
}