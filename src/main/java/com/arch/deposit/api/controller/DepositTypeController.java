package com.arch.deposit.api.controller;

import com.arch.deposit.api.dto.deposit_type.DepositTypeCreateDTO;
import com.arch.deposit.api.dto.deposit_type.DepositTypeResponseDTO;
import com.arch.deposit.api.dto.deposit_type.DepositTypeUpdateDTO;
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

    private final DepositTypeService depositTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepositTypeResponseDTO create(@Valid @RequestBody DepositTypeCreateDTO depositTypeCreateDTO) {
        return depositTypeService.create(depositTypeCreateDTO);
    }

    @GetMapping
    public List<DepositTypeResponseDTO> list() {
        return depositTypeService.list();
    }

    @GetMapping("/{id}")
    public DepositTypeResponseDTO get(@PathVariable UUID id) {
        return depositTypeService.get(id);
    }

    @PutMapping("/{id}")
    public DepositTypeResponseDTO update(@PathVariable UUID id,
                                      @Valid @RequestBody DepositTypeUpdateDTO depositTypeUpdateDTO) {
        return depositTypeService.update(id, depositTypeUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        depositTypeService.delete(id);
    }
}
