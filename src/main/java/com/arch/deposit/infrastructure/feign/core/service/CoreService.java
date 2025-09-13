package com.arch.deposit.infrastructure.feign.core.service;

import com.arch.deposit.infrastructure.feign.core.dto.CoreCustomerDepositsResponseDTO;
import com.arch.deposit.infrastructure.feign.core.dto.CoreDepositTypeResponseDTO;

import java.util.List;

public interface CoreService {
    List<CoreDepositTypeResponseDTO> getDepositTypes();
    CoreCustomerDepositsResponseDTO getCustomerDeposits(String userId);
}
