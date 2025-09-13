package com.arch.deposit.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a deposit account retrieved from the core banking system.
 */
@Entity
@Table(name = "deposit_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deposit {

    @Id
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

