package com.arch.deposit.controller;

import com.arch.deposit.deposittype.DepositType;
import com.arch.deposit.deposittype.DepositTypeRepository;
import com.arch.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deposits")
@RequiredArgsConstructor
public class DepositController {

    private final DepositTypeRepository typeRepository;
    private final DepositService depositService;

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