//package com.sm.backend;
/// I have doubt ;
//import com.sm.backend.model.Product;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.ArgumentsProvider;
//
//import java.util.stream.Stream;
//
//public class ProductArgumentProvider implements ArgumentsProvider {
//    @Override
//    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
//        return Stream.of(
//                Arguments.of(Product.builder().productPrice(450.00).productName("buildByTesting").build())
//        );
//    }
//}