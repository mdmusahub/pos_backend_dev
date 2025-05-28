//package com.sm.backend;
//
//import com.sm.backend.model.Product;
//import com.sm.backend.repository.ProductRepository;
//import com.sm.backend.request.ProductRequest;
//import com.sm.backend.serviceImpl.ProductServiceImpl;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ArgumentsSource;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class ProductServiceTest {
//private  final ProductRepository repository;
//private final ProductServiceImpl service;
//@Autowired
//    public ProductServiceTest(ProductRepository repository, ProductServiceImpl service) {
//        this.repository = repository;
//    this.service = service;
//}
//
//
//
//
//
//
//    @Test
//@Disabled
//
//public void findByID(){
//        Optional<Product> product = repository.findById(1l);
//
//        assertTrue(product.isPresent());
//
//Product product1 = product.get();
//
//assertNotNull(product1.getProductPrice());
//        assertNotNull(product1.getProductName());
//        assertNotNull(product1.getCategory());
//        System.out.println("test will pass");
//        }
//
//
////    @Disabled
////// I have  doubt in this method
////    @ParameterizedTest
////    @ArgumentsSource(ProductArgumentProvider.class)
////    public void testRegisterOfProduct(Product product) {
////        repository.save(product);
////
////    }
//
//
//
//
//
//// @ParameterizedTest
//// @Disabled
////@ValueSource(longs = {1,2,3})
////public void FindProductById(Long productId) {
////assertNotNull(repository.findById(1L),"failed for id" + productId);
//
//
//
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
