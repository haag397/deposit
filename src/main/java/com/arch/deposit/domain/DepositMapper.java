package com.arch.deposit.domain;

import com.arch.deposit.infrastructure.feign.core.dto.CoreCustomerDepositsResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface DepositMapper {

    // CREATE: DTO -> Entity
    @Mapping(target = "userId", expression = "java(userId)") // <-- set from parameter
    // Everything else with same name auto-maps.
    // String->Boolean: withdrawRight, isSpecial will use mapStringToBoolean(...)
    // String->BigDecimal: actualAmount/availableAmount will use mapStringToBigDecimal(...)
    // depositInterestRateInfo is String->String, no need to ignore
    // If signerInfo types differ, uncomment the next line to ignore for now:
    // @Mapping(target = "signerInfo", ignore = true)
    Deposit toNewEntity(CoreCustomerDepositsResponseDTO src, String userId);

    // UPDATE: ignore nulls so we don't overwrite existing values with null from Core
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userId", ignore = true) // don't overwrite userId during update
    // If signerInfo types differ, uncomment to ignore:
    // @Mapping(target = "signerInfo", ignore = true)
    void updateEntity(CoreCustomerDepositsResponseDTO src, @MappingTarget Deposit target, String userId);

    // ---------- Converters that MapStruct will auto-pick by types ----------

    /** For String -> Boolean (withdrawRight, isSpecial, etc.) */
    default Boolean mapStringToBoolean(String value) {
        if (value == null) return null;
        return Boolean.parseBoolean(value);
    }

    /** For String -> BigDecimal (actualAmount, availableAmount, etc.) */
    default BigDecimal mapStringToBigDecimal(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return new BigDecimal(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}