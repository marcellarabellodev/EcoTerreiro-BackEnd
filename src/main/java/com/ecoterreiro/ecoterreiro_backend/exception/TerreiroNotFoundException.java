package com.ecoterreiro.ecoterreiro_backend.exception;

public class TerreiroNotFoundException extends RuntimeException {
    public TerreiroNotFoundException(String message) {
        super(message);
    }
}
