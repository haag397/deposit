package com.arch.deposit.domain;

import com.arch.deposit.api.dto.deposit_type.DepositTypeResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class DepositTypeMapper {
    public DepositTypeResponseDTO toResp(DepositType d) {
        return new DepositTypeResponseDTO(d.getId(), d.getName(), d.getDescription());
    }
}