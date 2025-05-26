package com.sm.backend.repository;

import com.sm.backend.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant,Long> {
    @Query("select p from ProductVariant p where p.productId.id=:productId")
    List<ProductVariant> FindAllVariantsByProductId(@Param("productId")Long productId);
}
