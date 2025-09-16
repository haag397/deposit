package com.arch.deposit.infrastructure.feign.core.dto;

// Response envelope from Core (success and business errors)
public record CoreCreateDepositResponse(
        Result result,
        Status status,
        Meta meta
) {
    public record Result(Data data, CoreInnerStatus status) {
        public record Data(
                String transactionDate,
                String depositNumber,
                String currentAmount,
                String currentWithdrawableAmount,
                String iban
        ) {}
        public record CoreInnerStatus(String code, String message, String description) {}
    }
    public record Status(Integer code, String message, String description) {}
    public record Meta(String transactionId) {}
}

