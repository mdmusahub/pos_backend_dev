package com.sm.backend.repository;

import com.sm.backend.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
    @Query("select d from Discount d where d.isActive=true and variant.id=:variantId ")
    Optional<Discount> findDiscountByVariantId(Long variantId);
    //Optional<Discount> findDiscountByIsActive();
}
