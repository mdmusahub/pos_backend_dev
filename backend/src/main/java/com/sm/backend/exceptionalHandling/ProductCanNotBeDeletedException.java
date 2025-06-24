package com.sm.backend.exceptionalHandling;

public class ProductCanNotBeDeletedException extends RuntimeException {
    public ProductCanNotBeDeletedException(String message) {
        super(message);
    }
}
