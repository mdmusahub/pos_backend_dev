package com.sm.backend.factory.returnOrder;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Order;
import com.sm.backend.model.ReturnOrder;
import com.sm.backend.model.ReturnOrderItem;
import com.sm.backend.repository.ReturnOrderItemRepository;
import com.sm.backend.repository.ReturnOrderRepository;
import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.response.ReturnOrderResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteReturnOrder implements ReturnOperation{

    private final ReturnOrderRepository returnOrderRepository;
    private final ReturnOrderItemRepository returnOrderItemRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.DELETE;
    }

    @Override
    public ReturnOrderResponse execute(ReturnOrderRequest request) {
        ReturnOrder returnOrder =
                returnOrderRepository.findById(request.getReturnId())
                        .orElseThrow(()->
                new ResourceNotFoundException("Id not found"));
        Order order = returnOrder.getOrder();
        List<ReturnOrderItem> returnOrderItem = returnOrderItemRepository
                .findAllReturnOrderItemsByOrder(order);
        returnOrderItemRepository.deleteAll(returnOrderItem);
        returnOrderRepository.delete(returnOrder);
        return ReturnOrderResponse.builder()
                .message("Deleted return order with id " + request.getReturnId())
                .build();
    }
}
