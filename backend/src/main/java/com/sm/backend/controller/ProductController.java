package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createProduct(@RequestBody ProductRequest request) {
        service.createProduct(request);
    }

    @GetMapping("/getById/{productId}")

    public ResponseEntity<?> getById(@PathVariable Long productId) {
        try {
            return ResponseHandler.responseHandler("id retrieved successfully", HttpStatus.OK, service.getById(productId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public List<ProductResponse> getAll() {
        try {
            return  service.getAll();
        } catch (Exception e) {
            throw new  ResourceNotFoundException("Something went wrong ");
        }

    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?>update(@RequestBody ProductRequest request,@PathVariable Long productId ){
        try {
            return ResponseHandler.responseHandler("Product updated successfully",
                    HttpStatus.OK,service.update(request,productId));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
@DeleteMapping("/delete/{productId}")
public String delete(@PathVariable Long productId){
        service.delete(productId);
return "deleted Successfully";
    }


}
