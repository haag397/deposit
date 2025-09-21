package com.arch.deposit.domain;

import com.arch.deposit.api.dto.DepositTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class DepositTypeMapper {
    public DepositTypeResponse toResp(DepositType d) {
        return new DepositTypeResponse(d.getId(), d.getName(), d.getDescription());
    }
}