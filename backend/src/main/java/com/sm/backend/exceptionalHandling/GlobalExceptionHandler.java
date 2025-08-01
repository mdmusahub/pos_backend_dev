package com.sm.backend.exceptionalHandling;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(value = ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request){
    return new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(),e.getMessage(),request.getRequestURI());
}
@ExceptionHandler(value = CategoryAlreadyExistsException.class)
    public ErrorResponse handleCategoryAlreadyExistsException(CategoryAlreadyExistsException e,HttpServletRequest request){
    return new ErrorResponse(LocalDateTime.now(),HttpStatus.CONFLICT.value(),HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), request.getRequestURI());
}
@ExceptionHandler(value = ProductAlreadyExistsException.class)
    public ErrorResponse handleProductNotFoundException(ProductAlreadyExistsException e,HttpServletRequest request){
    return new ErrorResponse(LocalDateTime.now(),HttpStatus.CONFLICT.value(),HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), request.getRequestURI());
}
@ExceptionHandler(value = ProductCanNotBeDeletedException.class)
    public ErrorResponse handleProductCanNotBeDeletedException(ProductCanNotBeDeletedException e,HttpServletRequest request){
    return new ErrorResponse(LocalDateTime.now(),HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), e.getMessage(), request.getRequestURI());
}
@ExceptionHandler(value = DiscountAlreadyExistException.class)
public ErrorResponse handleDiscountAlreadyExistException(DiscountAlreadyExistException e,HttpServletRequest request){
    return new ErrorResponse(LocalDateTime.now(),HttpStatus.FORBIDDEN.value(),HttpStatus.FORBIDDEN.getReasonPhrase(), e.getMessage(), request.getRequestURI());
}}

