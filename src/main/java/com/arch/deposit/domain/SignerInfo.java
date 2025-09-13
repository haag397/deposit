package com.arch.deposit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignerInfo {
    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "portion")
    private String portion;

    @Column(name = "customer_relation_with_deposit_persian")
    private String customerRelationWithDepositPersian;

    @Column(name = "costomer_relation_with_deposit_english")
    private String customerRelationWithDepositEnglish;

    @Column(name = "full_name")
    private String fullName;
}