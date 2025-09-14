package com.arch.deposit.api.controller;

import com.arch.deposit.domain.DepositType;
import com.arch.deposit.domain.DepositTypeSyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/deposit-types")
@RequiredArgsConstructor
public class DepositTypeController {

    private final DepositTypeSyncService syncService;

    // Call this to fetch from Core, upsert by name, and return ALL deposit types from DB
    @PostMapping("/sync")
    public ResponseEntity<List<DepositType>> sync() {
        var all = syncService.syncAndReturnAll();
        return ResponseEntity.ok(all);
    }
}