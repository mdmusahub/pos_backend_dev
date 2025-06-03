package com.sm.backend.responseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandle {
    public static ResponseEntity<?> responseHandle(String message, HttpStatus httpStatus, Object object){
        Map<String,Object> res= new HashMap<>();
        res.put("message",message);
        res.put("HttpStatus",200);
        res.put("Object",object);
        return new ResponseEntity<>(res,httpStatus);
    }
}
