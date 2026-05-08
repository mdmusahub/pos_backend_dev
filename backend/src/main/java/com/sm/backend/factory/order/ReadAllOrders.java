package com.sm.backend.factory.order;

import com.sm.backend.model.Order;
import com.sm.backend.repository.OrderRepository;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllOrders implements OrderOperation{

    private final OrderRepository orderRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_ALL;
    }

    @Override
    public OrderResponse execute(OrderRequest request) {

        List<Order> all = orderRepository.findAll();
        return OrderResponse.builder()
                .orders(all.stream().map(OrderResponse::new).toList())
                .build();
    }
}
