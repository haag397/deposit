package com.arch.deposit.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "deposit_open_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositOpenRequest {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    private DepositOpeningStatus status;           // SUCCESS / FAILED / ERROR
    @Column(name = "transaction_date")
    private String transactionDate;
    @Column(name = "deposit_number")
    private String depositNumber;
    @Column(name = "current_amount")
    private String currentAmount;
    @Column(name = "current_withdrawable_amount")
    private String currentWithdrawableAmount;
    @Column(name = "transaction_id", length = 128)
    private String transactionId;
    @Column(name = "status_message", length = 128)
    private String StatusMessage;
    @Column(name = "deposit_type", length = 32)
    private String deposType;
    @Column(name = "customer_number", length = 64)
    private String customerNumber;
}