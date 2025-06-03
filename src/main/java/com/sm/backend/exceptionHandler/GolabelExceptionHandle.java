package com.sm.backend.exceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GolabelExceptionHandle {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ErrorResponse handleResourceException(ResourceNotFoundException r, HttpServletRequest request){
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND
                .getReasonPhrase(),r.getMessage(),request.getRequestURI());
    }

    @ExceptionHandler(value = CategoryAlreadyExistsException.class)
    public ErrorResponse handleAlreadyExistsException(CategoryAlreadyExistsException c, HttpServletRequest request){
        return  new ErrorResponse(LocalDateTime.now(),HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT
                .getReasonPhrase(),c.getMessage(),request.getRequestURI());
    }
}
