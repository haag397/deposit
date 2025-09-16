package com.arch.deposit.infrastructure.feign.core.service;

import com.arch.deposit.infrastructure.feign.core.dto.*;

import java.util.List;

public interface CoreService {
    List<CoreDepositTypeResponseDTO> getDepositTypes();
    CoreCustomerDepositsResponseDTO getCustomerDeposits(String userId);
    CoreCreateDepositResponse createDepositWithInterest(CreateDepositWithTransferReq req);
    CoreCreateDepositResponse createDeposit(CreateDepositSimpleReq req);

    /** Optional convenience: choose endpoint automatically based on request content. */
    CoreCreateDepositResponse createDepositSmart(CreateDepositWithTransferReq req);}
