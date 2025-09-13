package com.arch.deposit.deposittype;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arch.deposit.core.CoreClient;
import com.arch.deposit.core.CoreDepositTypeDTO;

import lombok.RequiredArgsConstructor;

/**
 * Synchronizes deposit types from the core banking system and stores them
 * locally for faster access. The name acts as the unique key.
 */
@Service
@RequiredArgsConstructor
public class DepositTypeSyncService {

    private final CoreClient coreClient;
    private final DepositTypeRepository repository;

    /**
     * Fetch types from Core, upsert by name, and return all stored types.
     */
    @Transactional
    public List<DepositType> syncAndReturnAll() {
        List<CoreDepositTypeDTO> dtos = requireNonNull(coreClient.getDepositTypes(), "Core returned null");

        for (CoreDepositTypeDTO dto : dtos) {
            if (dto.name() == null || dto.name().isBlank()) {
                continue;
            }
            String name = dto.name().trim();
            DepositType existing = repository.findByName(name).orElse(null);
            if (existing == null) {
                DepositType entity = DepositType.builder()
                        .name(name)
                        .description(dto.description())
                        .build();
                repository.save(entity);
            } else {
                existing.setDescription(dto.description());
                repository.save(existing);
            }
        }
        return repository.findAll();
    }
}
