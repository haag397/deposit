package com.arch.deposit.core;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for retrieving deposit types from the core banking service.
 */
@FeignClient(name = "core-client", url = "${core.service.url}")
public interface CoreClient {

    @GetMapping("/api/corebanking/deposits/v1.0/types")
    List<CoreDepositTypeDTO> getDepositTypes();

    /**
     * Retrieve deposit accounts for a given customer from the core banking system.
     */
    @GetMapping("/api/corebanking/deposits/v1.0/customers/{customerNumber}")
    List<CoreDepositDTO> getDepositsForCustomer(@PathVariable("customerNumber") String customerNumber);
}
