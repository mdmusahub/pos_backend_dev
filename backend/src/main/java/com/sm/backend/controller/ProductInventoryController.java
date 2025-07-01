package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ProductInventory")
@CrossOrigin(origins = "*")
public class ProductInventoryController {
    private final ProductInventoryService service;

    @Autowired
    public ProductInventoryController(ProductInventoryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createInventory(@RequestBody ProductInventoryRequest request) {
        service.createInventory(request);
    }

@GetMapping("/getAll")
private ResponseEntity<List<ProductInventoryResponse>>getAll(){
        try{
            return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
        }
        catch (Exception e){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{inventoryId}")
    public ResponseEntity<?>update(@RequestBody ProductInventoryRequest request,@PathVariable Long inventoryId){
        try {
            return ResponseHandler.responseHandler("Inventory updated successfully",HttpStatus.OK,service.update(request,inventoryId));

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
@GetMapping("/getById/{inventoryId}")
    public ResponseEntity<ProductInventoryResponse>getById(@PathVariable Long inventoryId){
        try {
            return new ResponseEntity<>(service.getById(inventoryId),HttpStatus.OK);

        } catch (Exception e) {
       throw  new ResourceNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{inventoryId}")
    public void delete(@PathVariable Long inventoryId){
        service.delete(inventoryId);
    }


}







