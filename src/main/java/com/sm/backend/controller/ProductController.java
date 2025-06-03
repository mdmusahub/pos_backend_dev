package com.sm.backend.controller;

import com.sm.backend.Request.ProductRequest;
import com.sm.backend.Service.ProductService;
import com.sm.backend.exceptionHandler.CategoryAlreadyExistsException;
import com.sm.backend.exceptionHandler.ResourceNotFoundException;
import com.sm.backend.model.Product;
import com.sm.backend.responseHandler.ResponseHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public Object findAll(@RequestParam(required = false,defaultValue = "0")Integer pageNumber,
                                 @RequestParam(required = false, defaultValue = "10")Integer pageSize)
    throws ResourceNotFoundException {
        try{
        return ResponseHandle.responseHandle("Show Data", HttpStatus.OK,
                productService.findAll(pageNumber, pageSize));
        }catch (Exception e){
            throw new ResourceNotFoundException("Error : "+e.getMessage());
        }
    }
    @GetMapping("/find")
    public Object findByNameAndPrice (@RequestParam (required = false) String name ,
                                             @RequestParam(required = false) Double price)
    throws ResourceNotFoundException{
        try {
        return ResponseHandle.responseHandle("Show Data",HttpStatus.OK,
                productService.findByNameAndPrice(name, price));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error : "+e.getMessage());
        }
    }
    @GetMapping("/name")
    public Object findByName (@RequestParam (required = false) String name)
    throws ResourceNotFoundException{
        try{
        return ResponseHandle.responseHandle("Show Data",HttpStatus.OK,
                productService.findByName(name));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error : "+e.getMessage());
        }
    }
    @GetMapping("/price")
    public List<Product> findByPrice (@RequestParam (required = false) Double price){
        return productService.findByPrice(price);
    }
    @PostMapping
    public void save (@RequestBody ProductRequest product){
        try{
        productService.addNew(product);
        }catch (Exception e){
            throw new RuntimeException("Error : "+e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) throws ResourceNotFoundException{
        try{
        return ResponseHandle.responseHandle("Find Suscessfull",HttpStatus.OK,
                productService.findById(id));
        }catch (Exception e){
            throw new ResourceNotFoundException("Error : "+e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update (@PathVariable Long id, @RequestBody ProductRequest productRequest)
    throws ResourceNotFoundException{
        try{
        return  ResponseHandle.responseHandle("Suscessfull Update",HttpStatus.OK,
                productService.update(id, productRequest));
        }catch (Exception e){
            throw new ResourceNotFoundException("Error : "+ e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable Long id) throws ResourceNotFoundException{
        try{
        return ResponseHandle.responseHandle("Delete Susccsfull",HttpStatus.OK,
                productService.deleteById(id));
        }catch (Exception e){
            throw new ResourceNotFoundException("Error : "+e.getMessage());
        }
    }
}
