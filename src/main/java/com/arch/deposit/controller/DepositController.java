package com.arch.deposit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import com.arch.deposit.deposittype.DepositType;
import com.arch.deposit.deposittype.DepositTypeRepository;
import com.arch.deposit.service.DepositService;

/** REST endpoints for the deposit opening flow. */
@RestController
@RequestMapping("/api/deposits")
public class DepositController {

    private final DepositTypeRepository typeRepository;
    private final DepositService depositService;

    public DepositController(DepositTypeRepository typeRepository, DepositService depositService) {
        this.typeRepository = typeRepository;
        this.depositService = depositService;
    }

    /** Returns available deposit types. */
    @GetMapping("/types")
    public List<DepositType> listTypes() {
        return typeRepository.findAll();
    }

    /** Step 1 – user selects a deposit type, starting the process. */
    @PostMapping("/{userId}/types/{typeId}")
    public String selectDepositType(@PathVariable String userId, @PathVariable UUID typeId) {
        return depositService.selectDepositType(userId, typeId);
    }

    /** Step 3 – user starts opening the previously selected deposit. */
    @PostMapping("/{userId}/{depositId}/start")
    public void startOpening(@PathVariable String userId, @PathVariable String depositId) {
        depositService.startDepositOpening(userId, depositId);
    }
}

