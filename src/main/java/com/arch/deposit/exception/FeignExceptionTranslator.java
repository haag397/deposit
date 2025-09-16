package com.arch.deposit.exception;

import feign.FeignException;

public final class FeignExceptionTranslator {

    private FeignExceptionTranslator() { }

    public static RuntimeException toCoreException(FeignException e, String operation) {
        // If you later need to parse e.contentUTF8() for structured errors, do it here
        return new CoreGatewayException(
                "Core call failed in " + operation + ": " + e.status() + " " + e.getMessage(),
                e);
    }
}