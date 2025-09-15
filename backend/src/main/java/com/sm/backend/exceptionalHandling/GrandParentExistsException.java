package com.sm.backend.exceptionalHandling;

public class GrandParentExistsException extends RuntimeException{
    public GrandParentExistsException (String parent){
        super(parent);
    }
}
