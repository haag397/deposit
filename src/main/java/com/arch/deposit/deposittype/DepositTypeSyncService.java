package com.arch.deposit.deposittype;

import com.arch.deposit.infrastructure.feign.core.CoreClient;
import com.arch.deposit.infrastructure.feign.core.service.CoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class DepositTypeSyncService {

    private final CoreService coreService;
    private final DepositTypeRepository repository;

    /**
     * Fetch from Core, upsert by unique name, then return ALL deposit types in DB.
     */
    @Transactional
    public List<DepositType> syncAndReturnAll() {
        var dtos = requireNonNull(coreService.getDepositTypes(), "Core returned null");

        for (var dto : dtos) {
            if (dto.name() == null || dto.name().isBlank()) continue; // skip invalid rows
            var name = dto.name().trim();

            var existing = repository.findByName(name).orElse(null);
            if (existing == null) {
                // create new (UUID generated)
                var entity = DepositType.builder()
                        .name(name)
                        .description(dto.description())
                        .build();
                repository.save(entity);
            } else {
                // update description only (name is unique key)
                existing.setDescription(dto.description());
                repository.save(existing);
            }
        }

        // Return ALL rows currently in DB (after upserts)
        return repository.findAll();
    }
}