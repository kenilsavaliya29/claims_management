package com.claims.common.exception;

public class WrongStatusUpdate extends RuntimeException {
    public WrongStatusUpdate(String message) {
        super(message);
    }
}
