package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.repository.OrderItemRepository;
import com.sm.backend.response.OrderItemResponse;
import com.sm.backend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
   private final OrderItemRepository repository;
@Autowired
    public OrderItemServiceImpl(OrderItemRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<OrderItemResponse> getAll() {
        return repository.findAll().stream().map(OrderItemResponse::new).toList();
    }

    @Override
    public Object getById(Long orderItemId) {
        return repository.findById(orderItemId).orElseThrow(()->new
                ResourceNotFoundException("Invalid order item ID"));
    }
}
