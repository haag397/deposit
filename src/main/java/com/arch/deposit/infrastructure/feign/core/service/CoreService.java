package com.arch.deposit.infrastructure.feign.core.service;

import com.arch.deposit.infrastructure.feign.core.dto.CoreDepositTypeDTO;

import java.util.List;

public interface CoreService {
    List<CoreDepositTypeDTO> getDepositTypes();
}
