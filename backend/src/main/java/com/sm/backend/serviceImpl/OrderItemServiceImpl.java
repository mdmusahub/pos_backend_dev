package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.repository.OrderItemRepository;
import com.sm.backend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
   private final OrderItemRepository repository;
@Autowired
    public OrderItemServiceImpl(OrderItemRepository repository) {
        this.repository = repository;
    }


    @Override
    public Object findAll() {
        return repository.findAll();
    }

    @Override
    public Object findById(Long orderItemId) {
        return repository.findById(orderItemId).orElseThrow(()->new
                ResourceNotFoundException("not found"));
    }
}
