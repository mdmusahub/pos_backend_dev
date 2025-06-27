package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.DiscountAlreadyExistException;
import com.sm.backend.exceptionalHandling.ProductAlreadyExistsException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;
import com.sm.backend.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discount")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }
    @PostMapping("/create")
    public void createDiscount(@RequestBody DiscountRequest request){
        try {
            discountService.createDiscount(request);
        } catch (Exception e) {
            throw new DiscountAlreadyExistException(e.getMessage());
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<DiscountResponse>>getAllDiscounts(){
        try {
            return new ResponseEntity<>(discountService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException("cannot retrieve all discounts");
        }
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<DiscountResponse>getById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(discountService.getById(id),HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException("invalid id");
        }
    }
    @PutMapping("/update/{id}")
    public void update(@RequestBody DiscountRequest request,@PathVariable Long id ){
        discountService.update(request,id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        discountService.delete(id);
    }
}


