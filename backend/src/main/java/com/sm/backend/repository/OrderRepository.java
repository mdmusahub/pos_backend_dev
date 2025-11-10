package com.sm.backend.repository;

import com.sm.backend.model.Customer;
import com.sm.backend.model.Order;
import com.sm.backend.model.ReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllOrdersByCustomer (Customer customer);

}
