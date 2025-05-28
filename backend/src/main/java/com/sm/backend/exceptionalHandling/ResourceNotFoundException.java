package com.sm.backend.exceptionalHandling;

import lombok.Data;


public class ResourceNotFoundException extends RuntimeException{
public  ResourceNotFoundException(String message){
    super(message);
}
}
