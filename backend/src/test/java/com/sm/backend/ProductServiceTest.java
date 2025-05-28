package com.sm.backend;

import com.sm.backend.model.Product;
import com.sm.backend.repository.ProductRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {
private  final ProductRepository repository;
    @Autowired
    public ProductServiceTest(ProductRepository repository) {
        this.repository = repository;
    }






    @Test
    @Disabled
public void findByID(){
        Optional<Product> product = repository.findById(1l);

        assertTrue(product.isPresent());

Product product1 = product.get();

assertNotNull(product1.getProductId());
assertNotNull(product1.getProductPrice());
        assertNotNull(product1.getProductName());
        assertNotNull(product1.getCategory());
        System.out.println("test will pass");
        }
 @ParameterizedTest
@ValueSource(longs = {1,2,3})
public void FindProductById(Long productId) {
assertNotNull(repository.findById(1L),"failed for id" + productId);
 }




}



















