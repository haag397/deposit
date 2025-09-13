package com.arch.deposit.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"deposit\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deposit {
    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;
    @Column(name = "userId", nullable = false)
    private String userId;
    @Column(name = "deposit_number")
    private String depositNumber;
    @Column(name = "main_customer_number")
    private String mainCustomerNumber;
    @Column(name = "deposit_title")
    private String depositTitle;
    @Column(name = "deposit_type_number")
    private String depositTypeNumber;
    @Column(name = "deposit_type_title")
    private String depositTypeTitle;
    @Column(name = "customer_relation_with_deposit_persian")
    private String customerRelationWithDepositPersian;
    @Column(name = "customer_relation_with_deposit_english")
    private String customerRelationWithDepositEnglish;
    @Column(name = "deposit_state")
    private String depositState;
    @Column(name = "currency_name")
    private String currencyName;
    @Column(name = "currency_swift_code")
    private String currencySwiftCode;
    @Column(name = "withdraw_right")
    private Boolean withdrawRight;
    @Column(name = "branch_code")
    private String branchCode;
    @Column(name = "deposit_iban")
    private String depositIban;
    @Column(name = "portion")
    private String portion;
    @Column(name = "is_special")
    private Boolean isSpecial;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "individual_or_shared_deposit")
    private String individualOrSharedDeposit;
    @Column(name = "opening_date")
    private String openingDate;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "deposit_id")
    private List<SignerInfo> signerInfo;
    @Column(name = "is_commercial_deposit")
    private Boolean isCommercialDeposit;
    @Column(name = "withdraw_tools", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private java.util.List<String> withdrawTools;
    @Column(name = "follow_code")
    private String followCode;
    @Column(name = "available_amount")
    private BigDecimal availableAmount;
    @Column(name = "account_amount")
    private BigDecimal actualAmount;
    @Column(name = "current_without_cheque_book")
    private Boolean currentWithoutChequeBook;
    @Column(name = "withdraw_tool_codes", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private java.util.List<String> withdrawToolCodes;
    @Column(name = "deposit_right")
    private Boolean depositRight;
    @Column(name = "english_individual_or_shared_deposit")
    private String englishIndividualOrSharedDeposit;
    @Column(name = "deposit_identity")
    private String depositIdentity;
    @Column(name = "deposit_identity_code")
    private String depositIdentityCode;
    @Column(name = "deposit_interest_rate_info")
    private String depositInterestRateInfo;
    @Column(name = "withdraw_right_with_checque")
    private Boolean withdrawRightWithCheque;
    @Column(name = "deposit_type_tree_root")
    private String depositTypeTreeRoot;
    @Column(name = "deposit_type_tree_root_code")
    private String depositTypeTreeRootCode;
}
