package com.sm.backend.service;

import com.sm.backend.model.Category;
import com.sm.backend.model.Product;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    
    @Autowired
    public void setProductService(ProductRepository productRepository,
                                  CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> searchByNameAndPrice(String name,Double price){
        return productRepository.searchByNameAndPrice(name,price);
    }

    public List<Product> searchByName(String name){
        return productRepository.searchByName(name);
    }
    
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    
    public void addNew(ProductRequest product){
       Product product1 = new Product();
        Category category = categoryRepository.findById(product.getCategoryId()).orElseThrow(()->
                new RuntimeException("Id is not found"));
        product1.setName(product.getName());
        product1.setSku(product.getSku());
        product1.setCategoryId(category);
        product1.setPrice(product.getPrice());
        product1.setDescription(product.getDescription());
        product1.setCreateAt(LocalDateTime.now());
        product1.setUpdateAt(LocalDateTime.now());
        productRepository.save(product1);

        
    }
    
    public Optional<Product>findById(Long id){
        return productRepository.findById(id);


    }
    
    public ResponseEntity updateById(Long id,ProductRequest product){
        if (productRepository.existsById(id)){
            Product product1 = productRepository.findById(id).orElseThrow(()->
                    new RuntimeException("id not found"));
            if (product.getName() != null){
                product1.setName(product.getName());
            }
            if (product.getSku() != null){
                product1.setSku(product.getSku());
            }
            if (product.getCategoryId() != null){
                Category category = categoryRepository.findById(product.getCategoryId()).orElseThrow(()->
                        new RuntimeException("id not found"));
                product1.setCategoryId(category);
            }
            if (product.getPrice() != null){
                product1.setPrice(product.getPrice());
            }
            if (product.getDescription() != null){
                product1.setDescription(product.getDescription());
            }
            if (product.getCreatedAt() != null){
                product1.setCreateAt(LocalDateTime.now());
            }
            if (product.getUpdatedAt() != null){
                product1.setUpdateAt(LocalDateTime.now());
            }
            productRepository.save(product1);
            return ResponseEntity.status(200).body("update successfuly");
        }else {
            return ResponseEntity.status(400).body("id not found");
        }
    }

//    ----------------------------------------------------------------------------------------------------------
    public ResponseEntity delete (Long id){
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return ResponseEntity.status(200).body("Delete successufully");
        }else {
            return ResponseEntity.status(400).body(("id not found"));
        }
    }
}
