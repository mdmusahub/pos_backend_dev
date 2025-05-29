package com.sm.backend.controller;

import com.sm.backend.model.Product;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public List<Product> searchByNameAndPrice(@RequestParam String name,@RequestParam Double price){
        return productService.searchByNameAndPrice(name,price);
    }

    @GetMapping("/find")
    public List<Product>searchByName(@RequestParam(required = false) String name){
        return productService.searchByName(name);
    }

    @PostMapping
    public void addNew(@RequestBody ProductRequest product){
         productService.addNew(product);
    }

    @GetMapping
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> findById(@PathVariable Long id){
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update (@PathVariable Long id, @RequestBody ProductRequest product){
        return productService.updateById(id,product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return productService.delete(id);
    }
}
