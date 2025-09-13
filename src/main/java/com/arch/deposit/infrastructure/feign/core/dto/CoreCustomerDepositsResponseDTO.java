package com.arch.deposit.infrastructure.feign.core.dto;

import java.util.List;

public record CoreCustomerDepositsResponseDTO(
        String depositNumber,
        String mainCustomerNumber,
        String depositTitle,
        String depositTypeNumber,
        String depositTypeTitle,
        String customerRelationWithDepositPersian,
        String customerRelationWithDepositEnglish,
        String depositState,
        String currencyName,
        String currencySwiftCode,
        String withdrawRight,          // "true"/"false" as sent by the provider
        String branchCode,
        String depositIban,
        String portion,
        String isSpecial,              // "true"/"false"
        String fullName,
        String individualOrSharedDeposit,
        String openingDate,
        List<SignerInfoResponse> signerInfo,
        boolean isCommercialDeposit,
        List<String> withdrawTools,
        String followCode,
        String availableAmount,
        String actualAmount,
        boolean currentWithoutChequeBook,
        List<String> withdrawToolCodes,
        boolean depositRight,
        String englishIndividualOrSharedDeposit,
        String depositIdentity,
        String depositIdentityCode,

        Object depositInterestRateInfo, // always null in sample; Object keeps it generic

        boolean withdrawRightWithCheque,
        String depositTypeTreeRoot,
        String depositTypeTreeRootCode
) {
    /* ---------- nested DTOs ---------- */
    public record SignerInfoResponse(
            String customerNumber,
            String portion,
            String customerRelationWithDepositPersian,
            String customerRelationWithDepositEnglish,
            String fullName
    ) {}
}
