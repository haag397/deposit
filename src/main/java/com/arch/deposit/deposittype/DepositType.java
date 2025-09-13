package com.arch.deposit.deposittype;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a selectable type of deposit.
 * The same entity is used for both reading and writing.
 */
@Entity
@Table(name = "\"deposit\"")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositType {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    private String name;
    private String description;
}

