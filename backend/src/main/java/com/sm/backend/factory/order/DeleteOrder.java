package com.sm.backend.factory.order;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Order;
import com.sm.backend.model.OrderItem;
import com.sm.backend.model.ReturnOrder;
import com.sm.backend.model.ReturnOrderItem;
import com.sm.backend.repository.OrderItemRepository;
import com.sm.backend.repository.OrderRepository;
import com.sm.backend.repository.ReturnOrderItemRepository;
import com.sm.backend.repository.ReturnOrderRepository;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteOrder implements OrderOperation{

    private final OrderRepository orderRepository;
    private final ReturnOrderRepository returnOrderRepository;
    private final ReturnOrderItemRepository returnOrderItemRepository;
    private final OrderItemRepository orderItemRepository;


    @Override
    public RequestType getRequestType() {
        return RequestType.DELETE;
    }

    @Override
    public OrderResponse execute(OrderRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("invalid order ID"));
        Optional<ReturnOrder> returnOrderByOrder = returnOrderRepository.findReturnOrderByOrder(order);
        if (returnOrderByOrder.isPresent()) {
            returnOrderRepository.delete(returnOrderByOrder.get());
        }
        List<ReturnOrderItem> allReturnOrderItemsByOrder = returnOrderItemRepository.findAllReturnOrderItemsByOrder(order);
        returnOrderItemRepository.deleteAll(allReturnOrderItemsByOrder);
        List<OrderItem> orderItems = order.getOrderItems();
        orderRepository.delete(order);
        orderItemRepository.deleteAll(orderItems);

        return OrderResponse.builder()
                .message("Order with ID " + request.getOrderId() + " has been deleted successfully.")
                .build();
    }
}
