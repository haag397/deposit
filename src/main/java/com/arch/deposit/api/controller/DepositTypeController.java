package com.arch.deposit.api.controller;

import com.arch.deposit.api.dto.DepositTypeCreateDTO;
import com.arch.deposit.api.dto.DepositTypeResponse;
import com.arch.deposit.api.dto.DepositTypeUpdateDTO;
import com.arch.deposit.service.DepositTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deposit-types")
public class DepositTypeController {

    private final DepositTypeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepositTypeResponse create(@Valid @RequestBody DepositTypeCreateDTO req) {
        return service.create(req);
    }

    @GetMapping
    public List<DepositTypeResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public DepositTypeResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public DepositTypeResponse update(@PathVariable UUID id,
                                  @Valid @RequestBody DepositTypeUpdateDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}