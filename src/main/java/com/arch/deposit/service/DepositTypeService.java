package com.arch.deposit.service;

import com.arch.deposit.api.dto.deposit_type.DepositTypeCreateDTO;
import com.arch.deposit.api.dto.deposit_type.DepositTypeResponseDTO;
import com.arch.deposit.api.dto.deposit_type.DepositTypeUpdateDTO;
import com.arch.deposit.command.deposit_type.CreateDepositTypeCommand;
import com.arch.deposit.command.deposit_type.DeleteDepositTypeCommand;
import com.arch.deposit.command.deposit_type.UpdateDepositTypeCommand;
import com.arch.deposit.domain.DepositType;
import com.arch.deposit.domain.DepositTypeRepository;
import com.arch.deposit.query.ExistsDepositTypeByNameQuery;
import com.arch.deposit.query.GetDepositTypeQuery;
import com.arch.deposit.query.ListDepositTypesQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositTypeService {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final DepositTypeRepository depositTypeRepository;

    public DepositTypeResponseDTO create(DepositTypeCreateDTO req) {
        UUID id = UUID.randomUUID();
        if (depositTypeRepository.existsByNameIgnoreCase(req.getName())) {
            throw new RuntimeException();
        }

        CreateDepositTypeCommand command = CreateDepositTypeCommand.builder()
                .id(id)
                .name(req.getName())
                .description(req.getDescription())
                .build();
        commandGateway.sendAndWait(command);

        return DepositTypeResponseDTO.builder()
                .name(req.getName())
                .description(req.getDescription())
                .build();
    }

    public List<DepositTypeResponseDTO> list() {
        return queryGateway.query(
                new ListDepositTypesQuery(),
                ResponseTypes.multipleInstancesOf(DepositTypeResponseDTO.class)
        ).join();
    }

    public DepositTypeResponseDTO get(UUID id) {
        return queryGateway.query(
                new GetDepositTypeQuery(id),
                ResponseTypes.instanceOf(DepositTypeResponseDTO.class)
        ).join();
    }

    public DepositTypeResponseDTO update(UUID id, DepositTypeUpdateDTO depositTypeUpdateDTO) {
        // Optional: uniqueness check if name changing
        if (depositTypeUpdateDTO.name() != null && !depositTypeUpdateDTO.name().isBlank()) {
            boolean exists = queryGateway.query(
                    new ExistsDepositTypeByNameQuery(depositTypeUpdateDTO.name()),
                    ResponseTypes.instanceOf(Boolean.class)
            ).join();
            // If you want to allow same name for the same id, you’d add a query that excludes id.
            if (exists) throw new IllegalArgumentException("Deposit type name already exists: " + depositTypeUpdateDTO.name());
        }
        commandGateway.sendAndWait(new UpdateDepositTypeCommand(
                id,
                depositTypeUpdateDTO.name(),
                depositTypeUpdateDTO.description(),
                depositTypeUpdateDTO.code()));
        return get(id);
    }

    public void delete(UUID id) {
        commandGateway.sendAndWait(new DeleteDepositTypeCommand(id));
    }
}
