//package com.sm.backend;
//
//import com.sm.backend.model.Product;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.ArgumentsProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//import java.util.stream.Stream;
//
//public class ProductArgumentProvider implements ArgumentsProvider {
//
//    private final Product product;
//    @Autowired
//    public ProductArgumentProvider(Product product) {
//        this.product = product;
//    }
//
//    @Override
//    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
//        return Stream.of(
//        Arguments.of(Product.builder().productName("jsks").productId(8l).productPrice(6500.00).sku("45JHKG00").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).discription("this product is build for testing only").build())
//);    }
//}
