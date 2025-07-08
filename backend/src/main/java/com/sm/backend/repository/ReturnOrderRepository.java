package com.sm.backend.repository;

import com.sm.backend.model.ReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ReturnOrderRepository extends JpaRepository <ReturnOrder,Long> {
}
