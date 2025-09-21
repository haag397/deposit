package com.arch.deposit.infrastructure.feign.core.service;

import com.arch.deposit.infrastructure.feign.core.CoreClient;
import com.arch.deposit.infrastructure.feign.core.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.arch.deposit.exception.FeignExceptionTranslator.toCoreException;

@Service
@RequiredArgsConstructor
public class CoreServiceImpl implements CoreService{
    private final CoreClient coreClient;

//    @Override
//    public List<CoreDepositTypeResponseDTO> getDepositTypes() {
//        return coreClient.getDepositTypes();
//    }

    @Override
    public CoreCustomerDepositsResponseDTO getCustomerDeposits(String userId) {
        return coreClient.getCustomerDeposits(userId);
    }

    @Override
    public CoreCreateDepositResponse createDepositWithInterest(CreateDepositWithTransferReq req) {
        try {
            return coreClient.createDepositWithInterest(req);
        } catch (feign.FeignException e) {
            throw toCoreException(e, "createDepositWithInterest");
        }
    }
    @Override
    public CoreCreateDepositResponse createDeposit(CreateDepositSimpleReq req) {
        try {
            return coreClient.createDeposit(req);
        } catch (feign.FeignException e) {
            throw toCoreException(e, "createDeposit");
        }
    }
    @Override
    public CoreCreateDepositResponse createDepositSmart(CreateDepositWithTransferReq req) {
        boolean hasTransfer =
                (req.sourceDeposit() != null && !req.sourceDeposit().isBlank())
                        || (req.destDeposit()   != null && !req.destDeposit().isBlank());

        if (hasTransfer) {
            return createDepositWithInterest(req);
        } else {
            var simple = new CreateDepositSimpleReq(
                    req.deposType(),
                    req.currency(),
                    req.amount(),
                    req.customerNumber(),
                    req.currentBranchCode()
            );
            return createDeposit(simple);
        }
    }
}
