package com.arch.deposit.infrastructure.feign.core.dto;

// Response envelope from Core (success and business errors)
public record CoreCreateDepositResponse(
        Result result,
        HttpStatus status,
        Meta meta
) {
    public record Result(Data data, Status status) {
        public record Data(
                String transactionDate,
                String depositNumber,
                String currentAmount,
                String currentWithdrawableAmount,
                String iban
        ) {}
        public record Status(String code, String message, String description) {}
    }
    public record HttpStatus(Integer code, String message) {}
    public record Meta(String transactionId) {}
}

