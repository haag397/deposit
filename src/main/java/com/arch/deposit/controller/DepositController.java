package com.arch.deposit.controller;

import com.arch.deposit.domain.DepositType;
import com.arch.deposit.domain.DepositTypeRepository;
import com.arch.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deposits")
@RequiredArgsConstructor
public class DepositController {

    private final DepositTypeRepository typeRepository;
    private final DepositService depositService;

    /** Browse available deposit types (from your DB). */
    @GetMapping("/types")
    public List<DepositType> listTypes() {
        return typeRepository.findAll();
    }

    /** Step 0 – user clicks “open deposit”: create session & start BPMN instance. */
    @PostMapping("/{userId}/session")
    public String startSession(@PathVariable String userId) {
        return depositService.startSession(userId);
    }

    /** Step 1 – user selects a deposit type (do NOT create process here). */
    @PostMapping("/{userId}/{depositId}/types/{typeId}")
    public void selectDepositType(@PathVariable String userId,
                                  @PathVariable String depositId,
                                  @PathVariable UUID typeId) {
        // ensure type exists, otherwise 400
        typeRepository.findById(typeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown depositTypeId"));
        depositService.selectDepositType(userId, depositId, typeId);
    }

    /** Step 2 – user confirms starting the opening (publish message only). */
    @PostMapping("/{userId}/{depositId}/start")
    public void confirmStartOpening(@PathVariable String userId, @PathVariable String depositId) {
        depositService.confirmStartOpening(userId, depositId);
    }

    /** Step 3 – user accepts terms (now change domain state and publish). */
    @PostMapping("/{userId}/{depositId}/terms/accept")
    public void acceptTerms(@PathVariable String userId, @PathVariable String depositId) {
        depositService.acceptTerms(userId, depositId);
    }
}