package com.arch.deposit.infrastructure.feign.core;

import com.arch.deposit.infrastructure.feign.core.dto.CoreCustomerDepositsResponseDTO;
import com.arch.deposit.infrastructure.feign.core.dto.CoreDepositTypeResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "core-client", url = "http://localhost:8290")
public interface CoreClient {
    @GetMapping("/api/corebanking/deposits/v1.0/types")
    List<CoreDepositTypeResponseDTO> getDepositTypes();

    @GetMapping("/api/corebanking/deposits/v1.0/customer/{userId}")
    CoreCustomerDepositsResponseDTO getCustomerDeposits(@PathVariable("userId") String userId);
}
