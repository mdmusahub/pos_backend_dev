package com.sm.backend.repository;

import com.sm.backend.model.Order;
import com.sm.backend.model.ReturnOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnOrderItemRepository extends JpaRepository<ReturnOrderItem, Long>{

    List<ReturnOrderItem> findAllReturnOrderItemsByOrder(Order order);
}
