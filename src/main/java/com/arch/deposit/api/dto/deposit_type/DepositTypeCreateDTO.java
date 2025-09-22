package com.arch.deposit.api.dto.deposit_type;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositTypeCreateDTO {
        @NotBlank
        private String name;
        private String description;
        }