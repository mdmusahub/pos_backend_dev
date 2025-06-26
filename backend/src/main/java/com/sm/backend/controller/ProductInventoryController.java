package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ProductInventory")
public class ProductInventoryController {
    private final ProductInventoryService service;

    @Autowired
    public ProductInventoryController(ProductInventoryService service) {
        this.service = service;
    }


    @PostMapping("/register")
    public void register(@RequestBody ProductInventoryRequest request) {
        service.register(request);
    }


@GetMapping("/getAll")
private ResponseEntity<?>getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                @RequestParam(required = false, defaultValue = "inventoryId") String sortby,
                                @RequestParam(required = false, defaultValue = "asc") String sortDir){
        try{
            return ResponseHandler.responseHandler("there is list", HttpStatus.OK,service.getAll(pageNumber,pageSize,sortby,sortDir));
        }
        catch (Exception e){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("/update/{inventoryId}")
    public ResponseEntity<?>update(@RequestBody ProductInventoryRequest request,@PathVariable Long inventoryId){
        try {
            return ResponseHandler.responseHandler("updated",HttpStatus.OK,service.update(request,inventoryId));

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


@GetMapping("/findById/{inventoryId}")
    public ResponseEntity<?>findById(@PathVariable Long inventoryId){
        try {
            return ResponseHandler.responseHandler("id found",HttpStatus.OK,service.findById(inventoryId));

        } catch (Exception e) {
       throw  new ResourceNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{inventoryId}")
    public void delete(@PathVariable Long inventoryId){
        service.delete(inventoryId);
    }


}







