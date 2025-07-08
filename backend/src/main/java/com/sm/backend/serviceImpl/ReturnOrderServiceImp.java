package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Order;
import com.sm.backend.model.OrderItem;
import com.sm.backend.model.ReturnOrder;
import com.sm.backend.repository.OrderRepository;
import com.sm.backend.repository.ReturnOrderRepository;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.response.ReturnOrderResponse;
import com.sm.backend.service.ReturnOrderService;
import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReturnOrderServiceImp implements ReturnOrderService {

    private final ReturnOrderRepository returnOrderRepository;
    private final OrderRepository orderRepository;
    @Autowired
    public ReturnOrderServiceImp(ReturnOrderRepository returnOrderRepository,
                                 OrderRepository orderRepository) {
        this.returnOrderRepository = returnOrderRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void createReturnOrder(ReturnOrderRequest request) throws ResourceNotFoundException{
        ReturnOrder returnOrder = new ReturnOrder();


        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() ->
                new ResourceNotFoundException("Invalid id"));
        returnOrder.setOrderId(order);
        returnOrder.setReturnDate(LocalDateTime.now());
        returnOrder.setRefundAmount(0d);
        returnOrderRepository.save(returnOrder);

    }

    @Override
    public List<ReturnOrderResponse> findAll() {
        List<ReturnOrder> order = returnOrderRepository.findAll();
        List<ReturnOrderResponse> list = order.stream().map(ReturnOrderResponse::new).toList();
        return  list;
    }

    @Override
    public ReturnOrderResponse findById(Long id) {
        ReturnOrder returnOrder = returnOrderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Invalid id"));
        return new ReturnOrderResponse(returnOrder);
    }
}
