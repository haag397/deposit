package com.arch.deposit.api.controller;

import com.arch.deposit.api.dto.*;
import com.arch.deposit.domain.DepositType;
import com.arch.deposit.domain.DepositTypeRepository;
import com.arch.deposit.service.DepositAppService;
//import com.arch.deposit.service.DepositService;
import jakarta.validation.Valid;
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

//    private final DepositTypeRepository typeRepository;
//    private final DepositService depositService;

    private final DepositTypeRepository typeRepo;
    private final DepositAppService app;

    /** Browse available deposit types (from your DB). */
    @GetMapping("/types")
    public List<DepositType> listTypes() {
        return typeRepo.findAll();
    }

    @PostMapping("/session")
    public String startSession(@Valid @RequestBody StartSessionReq req) {
    return app.startSession(req.userId());
}

    // 1) Select deposit type
    @PostMapping("/types/select")
    public void selectType(@Valid @RequestBody SelectTypeReq req) {
        typeRepo.findById(req.typeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown depositTypeId"));
        app.selectDepositType(req.userId(), req.depositId(), req.typeId());
    }

    // 2) Start deposit opening
    @PostMapping("/start")
    public void startOpening(@Valid @RequestBody StartOpeningReq req) {
        app.startDepositOpening(req.userId(), req.depositId());
    }

    // 3) Accept rules (drives gateway)
    @PostMapping("/terms/accept")
    public void acceptRules(@Valid @RequestBody AcceptRulesReq req) {
        app.acceptRules(req.userId(), req.depositId(), req.accepted());
    }

    // Continue after accept
    @PostMapping("/after-accept/continue")
    public void continueAfterAccept(@Valid @RequestBody BasicReq req) {
        app.continueAfterAccept(req.userId(), req.depositId());
    }

    // Select deposit/product
    @PostMapping("/product/select")
    public void selectDeposit(@Valid @RequestBody SelectDepositReq req) {
        app.selectDeposit(req.userId(), req.depositId(), req);
    }

    // Continue after select
    @PostMapping("/product/continue")
    public void continueAfterSelect(@Valid @RequestBody BasicReq req) {
        app.continueAfterSelect(req.userId(), req.depositId());
    }

    // Enter amount
    @PostMapping("/amount")
    public void enterAmount(@Valid @RequestBody AmountReq req) {
        app.enterAmount(req.userId(), req.depositId(), req.amount());
    }

    // Confirm amount
    @PostMapping("/amount/confirm")
    public void confirmAmount(@Valid @RequestBody BasicReq req) {
        app.confirmAmount(req.userId(), req.depositId());
    }

    // Choose profit destination
    @PostMapping("/profit-destination")
    public void chooseProfitDestination(@Valid @RequestBody ProfitDestinationReq req) {
        app.chooseProfitDestination(req.userId(), req.depositId(), req);
    }

    // Continue after choose destination
    @PostMapping("/profit-destination/continue")
    public void continueAfterChooseDestination(@Valid @RequestBody BasicReq req) {
        app.continueAfterChooseDestination(req.userId(), req.depositId());
    }

    // Final confirm & open
    @PostMapping("/confirm-open")
    public void confirmAndOpen(@Valid @RequestBody FinalConfirmReq req) {
        app.confirmInfoAndOpen(req);
    }
}