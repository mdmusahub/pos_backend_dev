package com.sm.backend.repository;

import com.sm.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
@Query("select a from orderItem a where a.ordering.orderID =:orderId")
List<OrderItem>getItemsByOrderId( Long orderId);

}
