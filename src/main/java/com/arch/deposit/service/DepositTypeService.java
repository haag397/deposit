package com.arch.deposit.service;

import com.arch.deposit.api.dto.DepositTypeCreateDTO;
import com.arch.deposit.api.dto.DepositTypeResponse;
import com.arch.deposit.api.dto.DepositTypeUpdateDTO;
import com.arch.deposit.domain.DepositType;
import com.arch.deposit.domain.DepositTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DepositTypeService {

    private final DepositTypeRepository repo;

    public DepositTypeResponse create(DepositTypeCreateDTO req) {
        // simple uniqueness guard on name (optional but helpful)
        if (repo.existsByNameIgnoreCase(req.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Deposit type name already exists");
        }
        var entity = DepositType.builder()
                .name(req.name().trim())
                .description(req.description())
                .build();
        entity = repo.save(entity);
        return toResp(entity);
    }

    @Transactional(readOnly = true)
    public List<DepositTypeResponse> list() {
        return repo.findAll().stream().map(this::toResp).toList();
    }

    @Transactional(readOnly = true)
    public DepositTypeResponse get(UUID id) {
        return toResp(findOr404(id));
    }

    public DepositTypeResponse update(UUID id, DepositTypeUpdateDTO req) {
        var entity = findOr404(id);

        // check name conflict with others (if name changed)
        if (!entity.getName().equalsIgnoreCase(req.name())
                && repo.existsByNameIgnoreCase(req.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Deposit type name already exists");
        }

        entity.setName(req.name().trim());
        entity.setDescription(req.description());
        return toResp(entity); // JPA dirty checking will flush
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deposit type not found");
        }
        repo.deleteById(id);
    }

    // --- helpers ---
    private DepositType findOr404(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deposit type not found"));
    }

    private DepositTypeResponse toResp(DepositType d) {
        return new DepositTypeResponse(d.getId(), d.getName(), d.getDescription());
    }
}