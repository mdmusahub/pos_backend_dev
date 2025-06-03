package com.sm.backend.exceptionHandler;

public class CategoryAlreadyExistsException extends Exception{
    public CategoryAlreadyExistsException (String message){
        super(message);
    }
}
