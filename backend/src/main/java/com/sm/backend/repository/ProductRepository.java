package com.sm.backend.repository;

import com.sm.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
Optional<Product> findProductByProductName(String name);
}
