package com.arch.deposit.projection;

import com.arch.deposit.api.dto.deposit_type.DepositTypeResponseDTO;
import com.arch.deposit.domain.DepositType;
import com.arch.deposit.domain.DepositTypeRepository;
import com.arch.deposit.event.deposit_type.DepositTypeCreatedEvent;
import com.arch.deposit.event.deposit_type.DepositTypeDeletedEvent;
import com.arch.deposit.event.deposit_type.DepositTypeUpdatedEvent;
import com.arch.deposit.query.ExistsDepositTypeByNameQuery;
import com.arch.deposit.query.GetDepositTypeQuery;
import com.arch.deposit.query.ListDepositTypesQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DepositTypeProjection {

    private final DepositTypeRepository repo;

    @EventHandler
    @Transactional
    public void on(DepositTypeCreatedEvent depositTypeCreatedEvent) {
//        if (repo.findById(e.getId()) return; // idempotent
        var row = DepositType.builder()
                .id(depositTypeCreatedEvent.getId())
                .name(depositTypeCreatedEvent.getName())
                .description(depositTypeCreatedEvent.getDescription())
                .build();
        repo.save(row);
    }

    @EventHandler
    @Transactional
    public void on(DepositTypeUpdatedEvent depositTypeUpdatedEvent) {
        var row = repo.findById(depositTypeUpdatedEvent.getId()).orElseThrow();
        row.setName(depositTypeUpdatedEvent.getName());
        row.setDescription(depositTypeUpdatedEvent.getDescription());
        repo.save(row);
    }

    @EventHandler
    @Transactional
    public void on(DepositTypeDeletedEvent depositTypeDeletedEvent) {
        repo.deleteById(depositTypeDeletedEvent.getId());
    }

    // Query handlers
    @QueryHandler
    public DepositTypeResponseDTO handle(GetDepositTypeQuery getDepositTypeQuery) {
        var row = repo.findById(getDepositTypeQuery.id()).orElseThrow();
        return new DepositTypeResponseDTO(row.getId(), row.getName(), row.getDescription());
    }

    @QueryHandler
    public List<DepositTypeResponseDTO> handle(ListDepositTypesQuery listDepositTypesQuery) {
        return repo.findAll().stream()
                .map(r -> new DepositTypeResponseDTO(r.getId(), r.getName(), r.getDescription()))
                .toList();
    }

    @QueryHandler
    public Boolean handle(ExistsDepositTypeByNameQuery existsDepositTypeByNameQuery) {
        return repo.existsByNameIgnoreCase(existsDepositTypeByNameQuery.name());
    }
}
