package com.arch.deposit.infrastructure.feign.core;

import com.arch.deposit.infrastructure.feign.core.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "core-client", url = "http://localhost:8290/api/corebanking")
public interface CoreClient {
//    @GetMapping("/deposits/v1.0/types")
//    List<CoreDepositTypeResponseDTO> getDepositTypes();

    @GetMapping("/deposits/v1.0/customer/{userId}")
    CoreCustomerDepositsResponseDTO getCustomerDeposits(@PathVariable("userId") String userId);

    @PostMapping("/deposits/v1.0/deposit/interest")
    CoreCreateDepositResponse createDepositWithInterest(@RequestBody CreateDepositWithTransferReq req);

    @PostMapping("/deposits/v1.0/deposit")
    CoreCreateDepositResponse createDeposit(@RequestBody CreateDepositSimpleReq req);
}