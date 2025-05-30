package com.sm.backend.responseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<?> responseHandler(String message, HttpStatus httpStatus, Object responseObject){
        Map<String,Object> response = new HashMap<>();
response.put("message",message);
response.put("HttpStatus",200);
response.put("object",responseObject);

return new ResponseEntity<>(response,httpStatus);

    }




}
