package com.POS.POS.exceptionalHandaling;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobleExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
public ErrorResponse handerResourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
      return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND
              .getReasonPhrase(),e.getMessage(),request.getRequestURI());
}

public ErrorResponse categoryResponseHandelar(CategoryAlreadyExistsException e, HttpServletRequest request ){
        return new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),request.getRequestURI());
}

}
