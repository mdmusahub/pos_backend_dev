package com.sm.backend.repository;

import com.sm.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
//@Query("select a from orderItem a where a.order.id =:orderId")
////List<OrderItem>getItemsByOrderId( @Param("orderId") Long orderId);
//@Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId")
//List<OrderItem> getItemsByOrderId(@Param("orderId") Long orderId);
}



