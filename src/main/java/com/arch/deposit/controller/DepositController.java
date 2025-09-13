package com.arch.deposit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import com.arch.deposit.account.Deposit;
import com.arch.deposit.account.UserDepositSyncService;
import com.arch.deposit.deposittype.DepositType;
import com.arch.deposit.deposittype.DepositTypeSyncService;
import com.arch.deposit.service.DepositService;

/** REST endpoints for the deposit opening flow. */
@RestController
@RequestMapping("/api/deposits")
public class DepositController {

    private final DepositService depositService;
    private final DepositTypeSyncService typeSyncService;
    private final UserDepositSyncService depositSyncService;

    public DepositController(DepositService depositService,
                             DepositTypeSyncService typeSyncService,
                             UserDepositSyncService depositSyncService) {
        this.depositService = depositService;
        this.typeSyncService = typeSyncService;
        this.depositSyncService = depositSyncService;
    }

    /** Step 1 – user starts the deposit opening flow. */
    @PostMapping("/{userId}/start")
    public StartResponse start(@PathVariable String userId) {
        String depositId = depositService.startDepositOpening(userId);
        List<DepositType> types = typeSyncService.syncAndReturnAll();
        return new StartResponse(depositId, types);
    }

    /** Step 2 – user selects a deposit type. */
    @PostMapping("/{userId}/{depositId}/types/{typeId}")
    public void selectDepositType(@PathVariable String userId,
                                  @PathVariable String depositId,
                                  @PathVariable UUID typeId) {
        depositService.selectDepositType(userId, depositId, typeId);
    }

    /** Retrieve and store user deposit accounts from core banking. */
    @GetMapping("/{userId}/accounts")
    public List<Deposit> listUserDeposits(@PathVariable String userId) {
        return depositSyncService.refresh(userId);
    }

    /** Response returned when the flow is started. */
    public record StartResponse(String depositId, List<DepositType> depositTypes) {}
}

