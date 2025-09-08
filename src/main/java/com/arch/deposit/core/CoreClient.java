package com.arch.deposit.core;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign client for retrieving deposit types from the core banking service.
 */
@FeignClient(name = "core-client", url = "${core.service.url}")
public interface CoreClient {

    @GetMapping("/api/corebanking/deposits/v1.0/types")
    List<CoreDepositTypeDTO> getDepositTypes();
}
