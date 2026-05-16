package com.claims.common.exception;

public class ClaimNotFoundException extends RuntimeException {

    public ClaimNotFoundException(String message) {
        super(message);
    }
}
