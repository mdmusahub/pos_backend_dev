package com.sm.backend.repository;

import com.sm.backend.model.Order;
import com.sm.backend.model.ReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  ReturnOrderRepository extends JpaRepository <ReturnOrder,Long> {
    Optional <ReturnOrder> findReturnOrderByOrder (Order order);
}
