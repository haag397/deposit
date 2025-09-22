package com.arch.deposit.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "\"deposit-type\"")
@Builder
public class DepositType {
    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;
    private String name;
    private String description;
}
