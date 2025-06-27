package com.sm.backend.exceptionalHandling;

public class DiscountAlreadyExistException extends RuntimeException{
    public DiscountAlreadyExistException(String message) {
        super(message);
    }
}
