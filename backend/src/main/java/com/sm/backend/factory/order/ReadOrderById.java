package com.sm.backend.factory.order;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Order;
import com.sm.backend.repository.OrderRepository;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOrderById implements OrderOperation {

    private final OrderRepository orderRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_ID;
    }

    @Override
    public OrderResponse execute(OrderRequest request) {
        Order order =
                orderRepository.findById(
                        request.getOrderId()).orElseThrow(() ->
                        new ResourceNotFoundException("invalid order ID"));
        return new OrderResponse(order);
    }
}
