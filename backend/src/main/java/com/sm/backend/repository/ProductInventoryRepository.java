package com.sm.backend.repository;

import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory,Long> {
//    @Query("select a from productInventory a where a.productVariant.productVariantId=:variantId")
ProductInventory findProductInventoryByProductVariant(ProductVariant productVariant);
}
