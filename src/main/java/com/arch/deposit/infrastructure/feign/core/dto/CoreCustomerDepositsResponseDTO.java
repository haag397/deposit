package com.arch.deposit.infrastructure.feign.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CoreCustomerDepositsResponseDTO(

        String depositNumber,
        String mainCustomerNumber,
        String depositTitle,
        String depositTypeNumber,
        String depositTypeTitle,

        @JsonProperty("customerRelationWithDepositPersian")
        String customerRelationWithDepositPersian,

        @JsonProperty("customerRelationWithDepositEnglish")
        String customerRelationWithDepositEnglish,

        String depositState,
        String currencyName,
        String currencySwiftCode,

        @JsonProperty("withdrawRight")
        String withdrawRight,          // "true"/"false" as sent by the provider

        String branchCode,
        String depositIban,
        String portion,

        @JsonProperty("isSpecial")
        String isSpecial,              // "true"/"false"

        String fullName,
        String individualOrSharedDeposit,
        String openingDate,

        List<SignerInfoResponse> signerInfo,

        @JsonProperty("isCommercialDeposit")
        boolean isCommercialDeposit,

        List<String> withdrawTools,
        String followCode,
        String availableAmount,
        String actualAmount,

        @JsonProperty("currentWithoutChequeBook")
        boolean currentWithoutChequeBook,

        List<String> withdrawToolCodes,

        @JsonProperty("depositRight")
        boolean depositRight,

        String englishIndividualOrSharedDeposit,
        String depositIdentity,
        String depositIdentityCode,

        Object depositInterestRateInfo, // always null in sample; Object keeps it generic

        @JsonProperty("withdrawRightWithCheque")
        boolean withdrawRightWithCheque,

        String depositTypeTreeRoot,
        String depositTypeTreeRootCode
) {

    /* ---------- nested DTOs ---------- */

    public record SignerInfoResponse(
            String customerNumber,
            String portion,
            @JsonProperty("customerRelationWithDepositPersian") String customerRelationWithDepositPersian,
            @JsonProperty("customerRelationWithDepositEnglish") String customerRelationWithDepositEnglish,
            String fullName
    ) {}
}
