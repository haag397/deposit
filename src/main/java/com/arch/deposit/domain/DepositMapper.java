package com.arch.deposit.domain;

import com.arch.deposit.infrastructure.feign.core.dto.CoreCustomerDepositsResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface DepositMapper {

    Deposit toEntity(CoreCustomerDepositsResponseDTO src);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CoreCustomerDepositsResponseDTO src, @MappingTarget Deposit target);

    // Converters
    default Boolean mapStringToBoolean(String value) {
        return value == null ? null : Boolean.parseBoolean(value);
    }

    default BigDecimal mapStringToBigDecimal(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return new BigDecimal(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
