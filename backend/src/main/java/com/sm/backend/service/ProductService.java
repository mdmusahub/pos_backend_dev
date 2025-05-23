package com.sm.backend.service;


import com.sm.backend.model.Product;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity save(ProductRequest request){

        Product product=new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setCreateAt(LocalDateTime.now());
        product.setCategoryId(categoryRepository.findById(request.getCategoryId()).get());
        productRepository.save(product);
        return ResponseEntity.ok().body("product created");
    }


    public Optional<Product> getProduct(Long id){
        return productRepository.findById(id);
    }


    public List<ProductResponse> getAllProduct(){
        List<ProductResponse> productResponseList=new ArrayList<>();
        List<Product> productList=productRepository.findAll();
        for (Product product:productList){
            ProductResponse productResponse=new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponse.setDescription(product.getDescription());
            productResponse.setSku(product.getSku());
            productResponse.setCreatedAt(product.getCreateAt());
            productResponse.setUpdatedAt(product.getUpdateAt());
            productResponse.setCategoryId(product.getCategoryId());
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }

    public ResponseEntity deleteProduct(Long id){
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }
        return ResponseEntity.ok().body("product deleted");
    }

    public ResponseEntity updateProduct(Long id, ProductRequest request){
        Product product=productRepository.findById(id).get();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setUpdateAt(LocalDateTime.now());
        product.setCategoryId(categoryRepository.findById(request.getCategoryId()).get());
        productRepository.save(product);
        return ResponseEntity.ok().body("Product updated");
    }
}
