package com.arch.deposit.deposittype;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.arch.deposit.core.CoreClient;
import com.arch.deposit.core.CoreDepositTypeDTO;

/**
 * Synchronizes deposit types from the core banking service into the local
 * repository for faster access.
 */
@Component
class DepositTypeSyncService implements CommandLineRunner {

    private final CoreClient coreClient;
    private final DepositTypeRepository repository;

    DepositTypeSyncService(CoreClient coreClient, DepositTypeRepository repository) {
        this.coreClient = coreClient;
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        refreshFromCore();
    }

    void refreshFromCore() {
        List<CoreDepositTypeDTO> types = coreClient.getDepositTypes();
        List<DepositType> entities = types.stream()
                .map(dto -> DepositType.builder()
                        .id(dto.id())
                        .name(dto.name())
                        .description(dto.description())
                        .build())
                .toList();
        repository.saveAll(entities);
    }
}
