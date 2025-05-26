package com.sm.backend.controller;

import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.ProductInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class ProductInventoryController {
    private final ProductInventoryService service;

    public ProductInventoryController(ProductInventoryService service) {
        this.service = service;
    }
    @PostMapping("/addInventory")
    public void createInventory(@RequestBody ProductInventoryRequest request){
         service.addInventory(request);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        try {
            return ResponseHandler.responseBuilder("All items retrieved successfully.", HttpStatus.OK,service.getAll());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id){
        try {
            return ResponseHandler.responseBuilder("inventory retrieved successfully.",HttpStatus.OK,service.getById(id));
        } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?>update(@PathVariable Long id,@RequestBody ProductInventoryRequest request){
        try{
            return ResponseHandler.responseBuilder("Inventory updated successfully.",HttpStatus.OK,service.updateById(id,request));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }
}
