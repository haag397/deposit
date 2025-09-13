package com.arch.deposit.account;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arch.deposit.core.CoreClient;
import com.arch.deposit.core.CoreDepositDTO;

import lombok.RequiredArgsConstructor;

/**
 * Synchronizes user deposit accounts with the core banking system.
 */
@Service
@RequiredArgsConstructor
public class UserDepositSyncService {

    private final CoreClient coreClient;
    private final DepositRepository repository;

    /**
     * Refresh deposits for the given customer by pulling data from the core
     * service and storing it locally.
     */
    @Transactional
    public List<Deposit> refresh(String customerNumber) {
        List<CoreDepositDTO> dtos = coreClient.getDepositsForCustomer(customerNumber);
        repository.deleteByMainCustomerNumber(customerNumber);
        List<Deposit> deposits = dtos.stream()
                .map(dto -> Deposit.builder()
                        .depositNumber(dto.getDepositNumber())
                        .mainCustomerNumber(dto.getMainCustomerNumber())
                        .depositTitle(dto.getDepositTitle())
                        .depositTypeNumber(dto.getDepositTypeNumber())
                        .depositTypeTitle(dto.getDepositTypeTitle())
                        .depositState(dto.getDepositState())
                        .currencyName(dto.getCurrencyName())
                        .currencySwiftCode(dto.getCurrencySwiftCode())
                        .withdrawRight(dto.getWithdrawRight())
                        .branchCode(dto.getBranchCode())
                        .depositIban(dto.getDepositIban())
                        .availableAmount(dto.getAvailableAmount())
                        .openingDate(dto.getOpeningDate())
                        .portion(dto.getPortion())
                        .isSpecial(dto.getIsSpecial())
                        .fullName(dto.getFullName())
                        .individualOrSharedDeposit(dto.getIndividualOrSharedDeposit())
                        .actualAmount(dto.getActualAmount())
                        .depositRight(dto.getDepositRight())
                        .englishIndividualOrSharedDeposit(dto.getEnglishIndividualOrSharedDeposit())
                        .depositIdentity(dto.getDepositIdentity())
                        .depositIdentityCode(dto.getDepositIdentityCode())
                        .withdrawRightWithCheque(dto.getWithdrawRightWithCheque())
                        .depositTypeTreeRoot(dto.getDepositTypeTreeRoot())
                        .depositTypeTreeRootCode(dto.getDepositTypeTreeRootCode())
                        .build())
                .toList();
        return repository.saveAll(deposits);
    }

    public List<Deposit> findByCustomer(String customerNumber) {
        return repository.findByMainCustomerNumber(customerNumber);
    }
}

