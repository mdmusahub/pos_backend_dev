package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.reasponseHandler.ResponseHandler;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")

public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/registerProduct")
    public void register(@RequestBody ProductRequest request) {
        service.register(request);
    }

    @GetMapping("/getById/{productId}")

    public ResponseEntity<?> findById(@PathVariable Long productId) {
        try {
            return ResponseHandler.responseHandler("id retrive successfully", HttpStatus.OK, service.findById(productId));
        } catch (Exception e) {
            throw  new ResourceNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findall(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     @RequestParam(required = false, defaultValue = "productName") String sortby,
                                     @RequestParam(required = false, defaultValue = "asc") String sortDir) {
        try {
            return ResponseHandler.responseHandler("there is list", HttpStatus.OK, service.findall(pageNumber, pageSize, sortby, sortDir));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/updateDetails/{productId}")
    public ResponseEntity<?>updateDetails(@RequestBody ProductRequest request,@PathVariable Long productId ){
        try {
            return ResponseHandler.responseHandler("update",
                    HttpStatus.OK,service.updateDetails(request,productId));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
@DeleteMapping("/delete/{productId}")
public String delete(@PathVariable Long productId){
        service.delete(productId);
return "deleted";
    }


}
