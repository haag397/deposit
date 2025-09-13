package com.arch.deposit.domain;

import com.arch.deposit.infrastructure.feign.core.dto.CoreCustomerDepositsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface DepositMapper {

    // Create new entity from Core DTO
    Deposit toNewEntity(CoreCustomerDepositsResponseDTO src, String userId);

    // Update existing entity in place (ignores nulls from DTO)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CoreCustomerDepositsResponseDTO src, @MappingTarget Deposit target, String userId);

    // ---------- Converters ----------

    // String → Boolean
    default Boolean mapStringToBoolean(String value) {
        if (value == null) return null;
        return Boolean.parseBoolean(value);
    }

    // String → BigDecimal
    default BigDecimal mapStringToBigDecimal(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return new BigDecimal(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}