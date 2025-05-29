package com.sm.backend.repository;

import com.sm.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> searchByNameAndPrice (String name , Double price);
    List<Product> searchByName (String name);

}
