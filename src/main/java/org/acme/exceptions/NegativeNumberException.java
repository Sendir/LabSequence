package org.acme.exceptions;

public class NegativeNumberException extends RuntimeException {

    public NegativeNumberException(String message) {
        super(message);
    }
}
