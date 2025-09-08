package com.arch.deposit.infrastructure.feign.core;

import com.arch.deposit.infrastructure.feign.core.dto.CoreDepositTypeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "core-client", url = "http://localhost:8290")
public interface CoreClient {
    @GetMapping("/api/corebanking/deposits/v1.0/types")
    List<CoreDepositTypeDTO> getDepositTypes();
}

