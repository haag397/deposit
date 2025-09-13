package com.arch.deposit.core;

import lombok.Data;

/**
 * DTO representing a deposit account returned from the core banking system.
 */
@Data
public class CoreDepositDTO {
    private String depositNumber;
    private String mainCustomerNumber;
    private String depositTitle;
    private String depositTypeNumber;
    private String depositTypeTitle;
    private String depositState;
    private String currencyName;
    private String currencySwiftCode;
    private String withdrawRight;
    private String branchCode;
    private String depositIban;
    private String availableAmount;
    private String openingDate;
    private String portion;
    private String isSpecial;
    private String fullName;
    private String individualOrSharedDeposit;
    private String actualAmount;
    private String depositRight;
    private String englishIndividualOrSharedDeposit;
    private String depositIdentity;
    private String depositIdentityCode;
    private String withdrawRightWithCheque;
    private String depositTypeTreeRoot;
    private String depositTypeTreeRootCode;
}

