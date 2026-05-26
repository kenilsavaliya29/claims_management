package com.claims.common.exception;

public class WrongDocumentType extends RuntimeException {
    public WrongDocumentType(String message) {
        super(message);
    }
}
